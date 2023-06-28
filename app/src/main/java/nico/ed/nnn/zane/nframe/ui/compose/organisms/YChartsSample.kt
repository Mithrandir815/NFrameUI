package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData

/**
 * YCharts ライブラリのサンプル実装
 * @see <a href="https://github.com/yml-org/YCharts">YCharts</a>
 */
@Preview
@Composable
fun YChartsSample(modifier: Modifier = Modifier) {
    val maxRange = 10
    val barDatas = DataUtils.getBarChartData(5, maxRange)
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barDatas.size - 1)
        .bottomPadding(40.dp)
        .axisLabelAngle(20f)
        .labelData { index -> barDatas[index].label }
        .build()

    val yStepSize = 5
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()
    val barChartData = BarChartData(
        chartData = barDatas,
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )
    BarChart(
        modifier = modifier.height(350.dp),
        barChartData = barChartData
    )
}
