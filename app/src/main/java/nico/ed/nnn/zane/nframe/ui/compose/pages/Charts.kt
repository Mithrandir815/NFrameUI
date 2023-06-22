package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        Column(
            Modifier.padding(padding)
        ) {
            // TODO: ここにグラフを実装していく
        }
    }
}
