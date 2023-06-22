package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar

@Preview
@Composable
fun Charts() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.charts)
        }
    ) { padding ->
        ChartsSample(modifier = Modifier.padding(padding))
    }
}

@Preview
@Composable
private fun ChartsSample(modifier: Modifier = Modifier) {
    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                BarChartData.Bar(
                    label = "Bar Label1",
                    value = 100f,
                    color = Color.Red
                ),
                BarChartData.Bar(
                    label = "Bar Label1",
                    value = 100f,
                    color = Color.Blue
                )
            )
        ),
        modifier = modifier,
        animation = simpleChartAnimation(),
        barDrawer = SimpleBarDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        labelDrawer = SimpleValueDrawer()
    )
}
