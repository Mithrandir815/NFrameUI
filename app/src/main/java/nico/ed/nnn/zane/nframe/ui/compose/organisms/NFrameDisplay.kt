package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import nico.ed.nnn.zane.nframe.data.NFrameHeaderFixed

@Composable
fun NFrameDisplay(
    nFrameHeader: NFrameHeader,
    nBGMedia: NBGMedia,
    nFrameHeaderCard: NFrameHeaderCard,
    nFrameHeaderFixed: NFrameHeaderFixed,
    content: List<String>
) {
    val modifier = if (nFrameHeaderFixed == NFrameHeaderFixed.FIXED) {
        Modifier.verticalScroll(rememberScrollState())
    } else {
        Modifier
    }
    val lazyModifier = if (nFrameHeaderFixed == NFrameHeaderFixed.FIXED) {
        Modifier
    } else {
        Modifier.verticalScroll(rememberScrollState())
    }
    Column(modifier = modifier) {
        if (nFrameHeader == NFrameHeader.PRESENT) {
            when (nBGMedia) {
                NBGMedia.MOVIE -> {}
//                    NFrameExoPlayer(
//                    modifier = Modifier,
//                    uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//                )
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
                        )
                    }
                }
                else -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            if (nBGMedia == NBGMedia.SOLID) {
                                Color.Gray
                            } else {
                                Color.White
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (nFrameHeaderCard == NFrameHeaderCard.PRESENT) {
                        Box(
                            modifier = Modifier
                                .width(168.dp)
                                .height(100.dp)
                        )
                    }
                }
            }
        }
        LazyColumn(modifier = lazyModifier) {
            item(content) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                        .height(100.dp)
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
        nBGMedia = NBGMedia.IMAGE,
        nFrameHeaderCard = NFrameHeaderCard.PRESENT,
        nFrameHeaderFixed = NFrameHeaderFixed.FIXED,
        content = listOf("test", "test", "test", "test")
    )
}
