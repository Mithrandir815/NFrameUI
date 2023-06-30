package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.charts.plotwizard.animation.AnimationType
import com.charts.plotwizard.chartstyle.ChartStyle
import com.charts.plotwizard.getMockRangeList
import com.charts.plotwizard.ui.Chart

/**
 * PlotWizard ライブラリのサンプル実装
 * @see <a href="https://github.com/md0092651/PlotWizard">PlotWizard</a>
 */
@Preview
@Composable
fun PlotWizardSample(modifier: Modifier = Modifier) {
    Chart(
        chartListData = getMockRangeList(),
        animationType = AnimationType.Bouncy(10F),
        chartStyle = ChartStyle.BarChartStyle(
            chartBrush = listOf(Color.Red, Color.Blue),
            barCornerRadius = 20F,
            chartValueTextColor = Color.Black
        )
    )
}
