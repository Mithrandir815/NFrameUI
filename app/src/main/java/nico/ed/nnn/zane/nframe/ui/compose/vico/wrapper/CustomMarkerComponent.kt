package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import android.graphics.RectF
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.chart.values.ChartValuesProvider
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.context.DrawContext
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.half
import com.patrykandpatrick.vico.core.extension.orZero
import com.patrykandpatrick.vico.core.marker.DefaultMarkerLabelFormatter
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

/**
 * The default implementation of the [Marker] interface.
 *
 * @param label the [TextComponent] used to draw the label.
 * @param indicator an optional indicator drawn at a given point belonging to the data entry.
 * @param guideline an optional line drawn from the bottom of the chart to the bottom edge of the [label].
 */
open class CustomMarkerComponent(
    val label: TextComponent,
    private val indicator: Component?,
    private val guideline: LineComponent?,
) : Marker {

    private val tempBounds = RectF()

    private val TextComponent.tickSizeDp: Float
        get() = ((background as? ShapeComponent)?.shape as? MarkerCorneredShape)?.tickSizeDp.orZero

    /**
     * The indicator size (in dp).
     */
    var indicatorSizeDp: Float = 0f

    /**
     * An optional lambda function that allows for applying the color associated with a given data entry to a
     * [Component].
     */
    private var onApplyEntryColor: ((entryColor: Int) -> Unit)? = null

    /**
     * The [MarkerLabelFormatter] for this marker.
     */
    var labelFormatter: MarkerLabelFormatter = DefaultMarkerLabelFormatter

    override fun draw(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
        chartValuesProvider: ChartValuesProvider,
    ): Unit = with(context) {
        drawGuideline(context, bounds, markedEntries)

        markedEntries.forEachIndexed { _, model ->
            onApplyEntryColor?.invoke(model.color)
            indicator?.draw(
                context,
                model.location.x,
                model.location.y,
                model.location.x,
                model.location.y,
            )
        }
        drawLabel(context, bounds, markedEntries, chartValuesProvider.getChartValues())
    }

    private fun drawLabel(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
        chartValues: ChartValues,
    ): Unit = with(context) {
        val text = labelFormatter.getLabel(markedEntries, chartValues)
        val entryX = markedEntries.averageOf { it.location.x }
        val labelBounds = label.getTextBounds(context, text, outRect = tempBounds)
        val halfOfTextWidth = labelBounds.width().half
        val x = overrideXPositionToFit(entryX, bounds, halfOfTextWidth)
        this[MarkerCorneredShape.tickXKey] = entryX

        label.drawText(
            context = context,
            text = text,
            textX = x,
            textY = bounds.top - labelBounds.height() - label.tickSizeDp.pixels,
            verticalPosition = VerticalPosition.Bottom,
        )
    }

    private fun overrideXPositionToFit(
        xPosition: Float,
        bounds: RectF,
        halfOfTextWidth: Float,
    ): Float = when {
        xPosition - halfOfTextWidth < bounds.left -> bounds.left + halfOfTextWidth
        xPosition + halfOfTextWidth > bounds.right -> bounds.right - halfOfTextWidth
        else -> xPosition
    }

    private fun drawGuideline(
        context: DrawContext,
        bounds: RectF,
        markedEntries: List<Marker.EntryModel>,
    ) {
        markedEntries
            .map { it.location.x }
            .toSet()
            .forEach { x ->
                guideline?.drawVertical(
                    context,
                    bounds.top - indicatorSizeDp.half,
                    bounds.bottom,
                    x,
                )
            }
    }

    override fun getInsets(
        context: MeasureContext,
        outInsets: Insets,
        horizontalDimensions: HorizontalDimensions,
    ): Unit = with(context) {
        outInsets.top = label.getHeight(context) + label.tickSizeDp.pixels
    }

    private fun <T> Collection<T>.averageOf(selector: (T) -> Float): Float =
        fold(0f) { sum, element ->
            sum + selector(element)
        } / size
}
