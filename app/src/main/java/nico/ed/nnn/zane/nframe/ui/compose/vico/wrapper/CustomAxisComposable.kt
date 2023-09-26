package nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper

import android.graphics.Typeface
import android.text.Layout
import android.text.TextUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.axisLineComponent
import com.patrykandpatrick.vico.compose.axis.axisTickComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.text.TextComponent

/**
 * Creates and remembers a bottom axis (i.e., a [HorizontalAxis] with [AxisPosition.Horizontal.Bottom]).
 *
 * @param labels the [TextComponent] to use for the labels.
 * @param axis the [LineComponent] to use for the axis line.
 * @param tick the [LineComponent] to use for the ticks.
 * @param tickLength the length of the ticks.
 * @param guideline the [LineComponent] to use for the guidelines.
 * @param valueFormatter formats the labels.
 * @param sizeConstraint defines how the [HorizontalAxis] is to size itself.
 * @param titleComponent an optional [TextComponent] to use as the axis title.
 * @param title the axis title.
 * @param labelRotationDegrees the rotation of the axis labels (in degrees).
 * @param itemPlacer determines for what _x_ values the [HorizontalAxis] is to display labels, ticks, and guidelines.
 */
@Composable
fun rememberCustomBottomAxis(
    labels: List<TextComponent?>? = customAxisLabelComponent(),
    axis: LineComponent? = axisLineComponent(),
    tick: LineComponent? = axisTickComponent(),
    tickLength: Dp = currentChartStyle.axis.axisTickLength,
    guideline: LineComponent? = axisGuidelineComponent(),
    valueFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> = DecimalFormatAxisValueFormatter(),
    sizeConstraint: CustomAxis.SizeConstraint = CustomAxis.SizeConstraint.Auto(),
    titleComponent: TextComponent? = null,
    title: CharSequence? = null,
    labelRotationDegrees: Float = currentChartStyle.axis.axisLabelRotationDegrees,
    itemPlacer: AxisItemPlacer.Horizontal = remember { AxisItemPlacer.Horizontal.default() },
): CustomHorizontalAxis<AxisPosition.Horizontal.Bottom> =
    remember { createHorizontalAxis<AxisPosition.Horizontal.Bottom>() }.apply {
        this.labels = labels
        axisLine = axis
        this.tick = tick
        this.guideline = guideline
        this.valueFormatter = valueFormatter
        tickLengthDp = tickLength.value
        this.sizeConstraint = sizeConstraint
        this.labelRotationDegrees = labelRotationDegrees
        this.titleComponent = titleComponent
        this.title = title
        this.itemPlacer = itemPlacer
    }

/**
 * Creates a [TextComponent] to be used for axis labels.
 *
 * @param color the text color.
 * @param textSize the text size.
 * @param background an optional [ShapeComponent] to be displayed behind the text.
 * @param ellipsize the text truncation behavior.
 * @param lineCount the line count.
 * @param verticalPadding the amount of top and bottom padding between the text and the background.
 * @param horizontalPadding the amount of start and end padding between the text and the background.
 * @param verticalMargin the size of the top and bottom margins around the background.
 * @param horizontalMargin the size of the start and end margins around the background.
 * @param typeface the [Typeface] for the text.
 * @param textAlignment the text alignment.
 */
@Composable
fun customAxisLabelComponent(
    colors: List<Color> = listOf(currentChartStyle.axis.axisLabelColor),
    textSize: TextUnit = currentChartStyle.axis.axisLabelTextSize,
    background: ShapeComponent? = currentChartStyle.axis.axisLabelBackground,
    ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.END,
    lineCount: Int = currentChartStyle.axis.axisLabelLineCount,
    verticalPadding: Dp = currentChartStyle.axis.axisLabelVerticalPadding,
    horizontalPadding: Dp = currentChartStyle.axis.axisLabelHorizontalPadding,
    verticalMargin: Dp = currentChartStyle.axis.axisLabelVerticalMargin,
    horizontalMargin: Dp = currentChartStyle.axis.axisLabelHorizontalMargin,
    typeface: Typeface = currentChartStyle.axis.axisLabelTypeface,
    textAlignment: Layout.Alignment = currentChartStyle.axis.axisLabelTextAlignment,
): List<TextComponent> {
    val textComponents = mutableListOf<TextComponent>()
    colors.forEach {
        textComponents.add(
            textComponent(
                it,
                textSize,
                background,
                ellipsize,
                lineCount,
                dimensionsOf(horizontalPadding, verticalPadding),
                dimensionsOf(horizontalMargin, verticalMargin),
                typeface,
                textAlignment,
            )
        )
    }
    return textComponents
}
