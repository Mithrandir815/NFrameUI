package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.axisTickComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.AxisPosition.Vertical
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import nico.ed.nnn.zane.nframe.ui.theme.Blue300
import nico.ed.nnn.zane.nframe.ui.theme.Blue500

private const val COLOR_1_CODE = 0xffff5500
private const val THRESHOLD_LINE_VALUE = 3f

private val chartColors = listOf(Blue300, Blue500)
private const val GUIDELINE_GAP_LENGTH_DP = 3f
private const val GUIDELINE_DASH_LENGTH_DP = 3f

//fun textComponent(
//    color: Int = android.graphics.Color.BLACK,
//    textSize: TextUnit = DefaultDimens.TEXT_COMPONENT_TEXT_SIZE.sp,
//    background: ShapeComponent? = null,
//    ellipsize: TextUtils.TruncateAt? = null,
//    lineCount: Int = DEF_LABEL_LINE_COUNT,
//    padding: MutableDimensions = emptyDimensions(),
//    margins: MutableDimensions = emptyDimensions(),
//    typeface: Typeface? = null,
//    textAlignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
//): TextComponent = TextComponent().apply {
//    this.color = color
//    this.ellipsize
//    color = this@Builder.color
//    textSizeSp = this@Builder.textSizeSp
//    typeface = this@Builder.typeface
//    ellipsize = this@Builder.ellipsize
//    lineCount = this@Builder.lineCount
//    background = this@Builder.background
//    textAlignment = this@Builder.textAlignment
//    padding.set(this@Builder.padding)
//    margins.set(this@Builder.margins)
//}

@Composable
fun VicoColumnChart(
    targetVerticalAxisPosition: Vertical? = null,
    chartValues: List<Pair<String, Int>> = listOf()
) {
    val date: List<String> = chartValues.map { it.first }
    val count: List<Int> = chartValues.map { it.second }
    val model = entryModelOf(*count.toTypedArray())
    val bottomAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _ -> date[x.toInt() % date.size] }
    val thresholdLine = rememberThresholdLine()
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultColumns = currentChartStyle.columnChart.columns
        Chart(
            modifier = Modifier
                .width(336.dp)
                .height(94.dp),
            chart = columnChart(
                columns = remember(defaultColumns) {
                    defaultColumns.map {
                        // 最後の要素の場合は別の処理を行う
                        LineComponent(
                            it.color,
                            12f,
                            Shapes.roundedCornerShape(
                                topRightPercent = 30,
                                topLeftPercent = 30
                            ),
                        )

                    }
//                        defaultColumns.mapIndexed { index, lineComponent ->
//                        // 最後の要素の場合は別の処理を行う
//                        if (index == defaultColumns.size - 1) {
//                            LineComponent(
//                                Blue300.toArgb(),
//                                12f,
//                                Shapes.roundedCornerShape(
//                                    topRightPercent = 30,
//                                    topLeftPercent = 30
//                                ),
//                            )
//                        } else {
//                            LineComponent(
//                                lineComponent.color,
//                                12f,
//                                Shapes.roundedCornerShape(
//                                    topRightPercent = 30,
//                                    topLeftPercent = 30
//                                ),
//                            )
//                        }
//                    }
                },
                decorations = remember(thresholdLine) { listOf(thresholdLine) },
                targetVerticalAxisPosition = targetVerticalAxisPosition,
                spacing = 10.dp
            ),
            model = model,
            bottomAxis = rememberBottomAxis(
                label = axisLabelComponent(
                    textSize = 8.sp,
                    horizontalPadding = 0.dp,
                    horizontalMargin = 0.dp
                ),
                axis = null,
                guideline = null,
                tickLength = 0.dp,
                tick = axisTickComponent(thickness = 0.dp, strokeWidth = 0.dp),
                valueFormatter = bottomAxisValueFormatter
            ),
            marker = rememberMarker(),
        )
    }
}

@Composable
private fun rememberThresholdLine(): ThresholdBehindLine {
    val guidelineShape = DashedShape(
        Shapes.pillShape,
        GUIDELINE_DASH_LENGTH_DP,
        GUIDELINE_GAP_LENGTH_DP
    )
    val line = shapeComponent(
        color = Color.Gray.copy(0.1f),
        shape = guidelineShape
    )
    val label = textComponent(
        color = Color.Black.copy(alpha = 0.2f),
        textSize = 8.sp
    )
    return remember(line, label) {
        ThresholdBehindLine(
            thresholdValue = THRESHOLD_LINE_VALUE,
            lineComponent = line,
            labelComponent = label,
            minimumLineThicknessDp = 2f
        )
    }
}

@Preview
@Composable
private fun PreviewVicoColumnChart() {
    val sample = listOf(
        Pair("2", 4),
        Pair("3", 5),
        Pair("4", 6),
        Pair("5", 7),
        Pair("6", 7),
        Pair("7", 7)
    )

    VicoColumnChart(
        chartValues = sample
    )
}
