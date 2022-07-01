package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nico.ed.nnn.zane.nframe.data.NBGMedia
import nico.ed.nnn.zane.nframe.data.NFrameHeader
import nico.ed.nnn.zane.nframe.data.NFrameHeaderCard
import nico.ed.nnn.zane.nframe.data.NFrameHeaderFixed
import nico.ed.nnn.zane.nframe.ui.compose.atoms.NFrameBox

@Composable
fun NFrameDisplay(
    nFrameHeader: NFrameHeader,
    nBGMedia: NBGMedia,
    nFrameHeaderCard: NFrameHeaderCard,
    nFrameHeaderFixed: NFrameHeaderFixed,
    content: List<String>
) {
    Column {
        if (nFrameHeaderFixed == NFrameHeaderFixed.FIXED) {
            NFrameBox(
                nFrameHeader,
                nBGMedia,
                nFrameHeaderCard
            )
        }
        LazyColumn {
            item {
                if (nFrameHeaderFixed == NFrameHeaderFixed.MOVE) {
                    NFrameBox(
                        nFrameHeader,
                        nBGMedia,
                        nFrameHeaderCard
                    )
                }
            }
            items(content) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                        .height(100.dp)
                        .background(color = Color.Gray),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNFrameDisplay() {
    NFrameDisplay(
        nFrameHeader = NFrameHeader.PRESENT,
        nBGMedia = NBGMedia.MOVIE,
        nFrameHeaderCard = NFrameHeaderCard.PRESENT,
        nFrameHeaderFixed = NFrameHeaderFixed.MOVE,
        content = List(10) { "test$it" }
    )
}
