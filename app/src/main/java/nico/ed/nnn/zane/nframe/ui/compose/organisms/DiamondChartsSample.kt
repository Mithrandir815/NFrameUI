package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.diamondedge.charts.BarChart
import com.diamondedge.charts.Charts
import com.diamondedge.charts.DefaultData
import com.diamondedge.charts.Margins
import com.diamondedge.charts.RandomData
import com.diamondedge.charts.compose.ComposeGC

/**
 * DiamondCharts ライブラリのサンプル実装
 * @see <a href="https://github.com/ellsworthrw/DiamondCharts">DiamondCharts</a>
 */
@Preview
@Composable
fun DiamondChartsSample(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val data = RandomData(DefaultData.SIMPLE_SERIES, 3)
    Canvas(modifier = modifier.fillMaxSize()) {
        val charts = Charts(size.width, size.height, Margins.default, Charts.LEGEND_NONE)
        charts.add(BarChart(data, true, isStacked = true, is100Percent = false))

        drawIntoCanvas { canvas ->
            charts.draw(ComposeGC(canvas, density))
        }
    }
}
