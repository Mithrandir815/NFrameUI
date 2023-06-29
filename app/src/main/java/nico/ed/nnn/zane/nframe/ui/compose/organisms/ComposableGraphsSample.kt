package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jaikeerthick.composable_graphs.composables.BarGraph
import com.jaikeerthick.composable_graphs.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.style.BarGraphVisibility

/**
 * Composable-Graphs ライブラリのサンプル実装
 * @see <a href="https://github.com/jaikeerthick/Composable-Graphs">Composable-Graphs</a>
 */
@Preview
@Composable
fun ComposableGraphsSample(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        BarGraph(
            dataList = listOf(20, 30, 10, 60, 35),
            style = BarGraphStyle(
                visibility = BarGraphVisibility(
                    isYAxisLabelVisible = true
                )
            )
        )
    }
}
