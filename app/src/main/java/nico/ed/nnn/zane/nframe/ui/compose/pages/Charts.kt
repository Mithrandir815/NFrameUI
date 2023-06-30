package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar
import nico.ed.nnn.zane.nframe.ui.compose.organisms.DiamondChartsSample

@Preview
@Composable
fun Charts() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.charts)
        }
    ) { padding ->
        // ここにサンプル実装をお願いします
//        ChartsSample(modifier = Modifier.padding(padding))
//        YChartsSample(modifier = Modifier.padding(padding))
//        ComposeChartsSample(modifier = Modifier.padding(padding))
//        ComposableGraphsSample(modifier = Modifier.padding(padding))
        DiamondChartsSample(modifier = Modifier.padding(padding))
    }
}
