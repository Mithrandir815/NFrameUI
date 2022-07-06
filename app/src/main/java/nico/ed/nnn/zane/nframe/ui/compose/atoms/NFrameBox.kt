package nico.ed.nnn.zane.nframe.ui.compose.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NBGMedia
import nico.ed.nnn.zane.nframe.data.NFrameHeader
import nico.ed.nnn.zane.nframe.data.NFrameHeaderCard
import nico.ed.nnn.zane.nframe.ui.compose.organisms.NFrameExoPlayer

@Composable
fun NFrameBox(
    nFrameHeader: NFrameHeader,
    nBGMedia: NBGMedia,
    nFrameHeaderCard: NFrameHeaderCard,
    positionMs: Long,
    onPositionChange: (Long) -> Unit
) {
    if (nFrameHeader == NFrameHeader.PRESENT) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    if (nBGMedia == NBGMedia.SOLID) {
                        Color.Gray
                    } else {
                        Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            when (nBGMedia) {
                NBGMedia.MOVIE -> {
                    NFrameExoPlayer(
                        uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        positionMs = positionMs,
                        onPositionChange = onPositionChange,
                    )
                }
                NBGMedia.IMAGE -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sample),
                        modifier = Modifier
                            .fillMaxSize(),
                        contentDescription = null
                    )
                    if (nFrameHeaderCard == NFrameHeaderCard.PRESENT) {
                        Box(
                            modifier = Modifier
                                .width(168.dp)
                                .height(100.dp)
                                .background(color = Color.Gray)
                        )
                    }
                }
                else -> {}
            }
            if (nFrameHeaderCard == NFrameHeaderCard.PRESENT) {
                Box(
                    modifier = Modifier
                        .width(168.dp)
                        .height(100.dp)
                        .background(color = Color.Gray)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNFrameBox() {
    NFrameBox(
        nFrameHeader = NFrameHeader.PRESENT,
        nBGMedia = NBGMedia.MOVIE,
        nFrameHeaderCard = NFrameHeaderCard.PRESENT,
        positionMs = 0,
        onPositionChange = {},
    )
}
