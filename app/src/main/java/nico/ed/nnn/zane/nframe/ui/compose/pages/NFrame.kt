package nico.ed.nnn.zane.nframe.ui.compose.pages

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameTopAppBar

@Preview
@Composable
fun NFrame() {
    Scaffold(
        topBar = {
            NFrameTopAppBar(R.string.n_frame_settings)
        }
    ) {
    }
}
