package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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


private val chartColors = listOf(Blue300, Blue500)
private const val GUIDELINE_GAP_LENGTH_DP = 3f
private const val GUIDELINE_DASH_LENGTH_DP = 3f

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
    val thresholdLine = rememberThresholdLine((chartValues.sumOf { it.second } / 14f))
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        Chart(
            modifier = Modifier
                .width(336.dp)
                .height(94.dp),
            //作成するグラフの種類やグラフについて
            chart = columnChart(
                columns = listOf(
                    LineComponent(
                        Blue300.toArgb(),
                        12f,
                        Shapes.roundedCornerShape(
                            topRightPercent = 30,
                            topLeftPercent = 30
                        ),
                    ),
                    LineComponent(
                        Blue500.toArgb(),
                        12f,
                        Shapes.roundedCornerShape(
                            topRightPercent = 30,
                            topLeftPercent = 30
                        ),
                    )
                ),
                decorations = remember(thresholdLine) { listOf(thresholdLine) },
                targetVerticalAxisPosition = targetVerticalAxisPosition,
                spacing = 10.dp
            ),
            //チャートで使用するデータ
            model = model,
            //x軸のラベルなどを作成する要素
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
            //バルーンのを作成する要素
            marker = rememberMarker(),
        )
    }
}

@Composable
private fun rememberThresholdLine(ave: Float): ThresholdBehindLine {
    val guidelineShape = DashedShape(
        Shapes.pillShape,
        GUIDELINE_DASH_LENGTH_DP,
        GUIDELINE_GAP_LENGTH_DP
    )
    val line = shapeComponent(
        color = Color.Black.copy(alpha = 0.2f),
        shape = guidelineShape
    )
    val label = textComponent(
        color = Color.Black.copy(alpha = 0.2f),
        textSize = 8.sp
    )
    return remember(line, label) {
        ThresholdBehindLine(
            thresholdValue = ave,
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
