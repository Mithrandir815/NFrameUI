package nico.ed.nnn.zane.nframe.ui.compose.vico

import android.graphics.RectF
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.text.HorizontalPosition
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.extension.ceil
import com.patrykandpatrick.vico.core.extension.floor
import com.patrykandpatrick.vico.core.extension.getEnd
import com.patrykandpatrick.vico.core.extension.getStart
import com.patrykandpatrick.vico.core.extension.half
import com.patrykandpatrick.vico.core.extension.median
import java.text.DecimalFormat

/**
 * [ThresholdBehindLine] is drawn on top of charts and marks a certain range of y-axis values.
 *
 * @property thresholdRange the range of y-axis values that this [ThresholdBehindLine] will cover.
 * @property thresholdLabel the label of this [ThresholdBehindLine].
 * @property lineComponent the [ShapeComponent] drawn as the threshold line.
 * @property minimumLineThicknessDp the minimal thickness of the threshold line. If the [thresholdRange] implies
 * a smaller thickness, the [minimumLineThicknessDp] will be used as the threshold line’s thickness.
 * @property labelComponent the [TextComponent] used to draw the [thresholdLabel] text.
 * @property labelHorizontalPosition defines the horizontal position of the label.
 * @property labelVerticalPosition defines the vertical position of the label.
 * @property labelRotationDegrees the rotation of the label (in degrees).
 *
 * @see Decoration
 */
data class ThresholdBehindLine(
    val thresholdRange: ClosedFloatingPointRange<Float>,
    val thresholdLabel: CharSequence = RANGE_FORMAT.format(
        decimalFormat.format(thresholdRange.start),
        decimalFormat.format(thresholdRange.endInclusive),
    ),
    val lineComponent: ShapeComponent = ShapeComponent(),
    val minimumLineThicknessDp: Float = DefaultDimens.THRESHOLD_LINE_THICKNESS,
    val labelComponent: TextComponent = textComponent(),
    val labelHorizontalPosition: LabelHorizontalPosition = LabelHorizontalPosition.Start,
    val labelVerticalPosition: LabelVerticalPosition = LabelVerticalPosition.Top,
    val labelRotationDegrees: Float = 0f,
) : Decoration {

    /**
     * An alternative constructor that accepts a single y-axis value as opposed to a range.
     *
     * @property thresholdValue the value on the y-axis that this [ThresholdBehindLine] will cover.
     * @property thresholdLabel the label of this [ThresholdBehindLine].
     * @property lineComponent the [ShapeComponent] drawn as the threshold line.
     * @property minimumLineThicknessDp the minimal thickness of the threshold line. If the [thresholdRange] implies
     * a smaller thickness, the [minimumLineThicknessDp] will be used as the threshold line’s thickness.
     * @property labelComponent the [TextComponent] used to draw the [thresholdLabel] text.
     * @property labelHorizontalPosition defines the horizontal position of the label.
     * @property labelVerticalPosition defines the vertical position of the label.
     * @property labelRotationDegrees the rotation of the label (in degrees).
     */
    constructor(
        thresholdValue: Float,
        thresholdLabel: CharSequence = "平\n均",
        lineComponent: ShapeComponent = ShapeComponent(),
        minimumLineThicknessDp: Float = DefaultDimens.THRESHOLD_LINE_THICKNESS,
        labelComponent: TextComponent = textComponent(),
        labelHorizontalPosition: LabelHorizontalPosition = LabelHorizontalPosition.Start,
        labelVerticalPosition: LabelVerticalPosition = LabelVerticalPosition.Center,
        labelRotationDegrees: Float = 0f,
    ) : this(
        thresholdRange = thresholdValue..thresholdValue,
        thresholdLabel = thresholdLabel,
        lineComponent = lineComponent,
        minimumLineThicknessDp = minimumLineThicknessDp,
        labelComponent = labelComponent,
        labelHorizontalPosition = labelHorizontalPosition,
        labelVerticalPosition = labelVerticalPosition,
        labelRotationDegrees = labelRotationDegrees,
    )

    override fun onDrawBehindChart(
        context: ChartDrawContext,
        bounds: RectF,
    ): Unit = with(context) {
        val chartValues = chartValuesManager.getChartValues()

        val valueRange = chartValues.maxY - chartValues.minY

        val centerY =
            bounds.bottom - (thresholdRange.median - chartValues.minY) / valueRange * bounds.height()

        val topY = minOf(
            bounds.bottom - (thresholdRange.endInclusive - chartValues.minY) / valueRange * bounds.height(),
            centerY - minimumLineThicknessDp.pixels.half,
        ).ceil
        val bottomY = maxOf(
            bounds.bottom - (thresholdRange.start - chartValues.minY) / valueRange * bounds.height(),
            centerY + minimumLineThicknessDp.pixels.half,
        ).floor
        val textY = when (labelVerticalPosition) {
            LabelVerticalPosition.Top -> topY
            LabelVerticalPosition.Center -> centerY
            LabelVerticalPosition.Bottom -> bottomY
        }

        lineComponent.draw(
            context = context,
            // 平均線を短くしてその余白に平均がくるように設定
            left = bounds.left + 60f,
            right = bounds.right,
            top = topY,
            bottom = bottomY,
        )
        labelComponent.drawText(
            context = context,
            text = thresholdLabel,
            maxTextWidth = bounds.width().toInt(),
            textX = when (labelHorizontalPosition) {
                LabelHorizontalPosition.Start -> bounds.getStart(isLtr = isLtr)
                LabelHorizontalPosition.End -> bounds.getEnd(isLtr = isLtr)
            },
            textY = textY,
            horizontalPosition = labelHorizontalPosition.position,
            verticalPosition = labelVerticalPosition.position,
            rotationDegrees = labelRotationDegrees,
        )
    }

    /**
     * Defines the horizontal position of a [ThresholdBehindLine]’s label.
     *
     * @property position the label position.
     */
    enum class LabelHorizontalPosition(val position: HorizontalPosition) {
        Start(HorizontalPosition.End),
        End(HorizontalPosition.Start),
    }

    /**
     * Defines the vertical position of a [ThresholdBehindLine]’s label.
     *
     * @property position the label position.
     */
    enum class LabelVerticalPosition(val position: VerticalPosition) {
        Top(VerticalPosition.Top),
        Center(VerticalPosition.Center),
        Bottom(VerticalPosition.Bottom),
    }

    private companion object {
        const val RANGE_FORMAT = "%s–%s"
        val decimalFormat = DecimalFormat("#.##")
    }
}
