package nico.ed.nnn.zane.nframe.ui.compose.organisms

import android.graphics.Typeface
import android.graphics.Typeface.DEFAULT_BOLD
import android.text.TextUtils
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.axisTickComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.AxisPosition.Vertical
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import nico.ed.nnn.zane.nframe.ui.compose.vico.ThresholdBehindLine
import nico.ed.nnn.zane.nframe.ui.compose.vico.rememberMarker
import nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper.Chart
import nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper.customColumnChart
import nico.ed.nnn.zane.nframe.ui.compose.vico.wrapper.rememberCustomBottomAxis
import nico.ed.nnn.zane.nframe.ui.theme.Blue300
import nico.ed.nnn.zane.nframe.ui.theme.Blue500
import nico.ed.nnn.zane.nframe.ui.theme.Gray500


private val chartColors = listOf(Blue300, Blue500)
private const val GUIDELINE_GAP_LENGTH_DP = 3f
private const val GUIDELINE_DASH_LENGTH_DP = 3f
val robotoCondensed: Typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)

@Composable
fun VicoColumnChart(
    targetVerticalAxisPosition: Vertical? = null,
    chartValues: List<Pair<String, Int>> = listOf(),
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
                .width(360.dp)
                .height(140.dp),
            chart = customColumnChart(
                columns = listOf(
                    LineComponent(
                        Blue300.toArgb(),
                        14f,
                        Shapes.roundedCornerShape(
                            topRightPercent = 30,
                            topLeftPercent = 30
                        ),
                    ),
                    LineComponent(
                        Blue500.toArgb(),
                        14f,
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
            bottomAxis = rememberCustomBottomAxis(
                labels = listOf(
                    axisLabelComponent(
                        color = Gray500,
                        textSize = 10.sp,
                        horizontalPadding = 0.dp,
                        horizontalMargin = 0.dp,
                        ellipsize = TextUtils.TruncateAt.MARQUEE,
                        typeface = robotoCondensed
                    ), axisLabelComponent(
                        color = Blue500,
                        textSize = 10.sp,
                        horizontalPadding = 0.dp,
                        horizontalMargin = 0.dp,
                        typeface = robotoCondensed
                    )
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
        textSize = 8.sp,
        lineCount = 2,
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
        Pair("25", 5),
        Pair("4", 6),
        Pair("5", 7),
        Pair("6", 7),
        Pair("7", 7),
        Pair("9", 20)
    )

    VicoColumnChart(
        chartValues = sample
    )
}
