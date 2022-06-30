package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import nico.ed.nnn.zane.nframe.R
import nico.ed.nnn.zane.nframe.data.NCardMedia
import nico.ed.nnn.zane.nframe.ui.theme.Gray400
import nico.ed.nnn.zane.nframe.ui.theme.Gray500

@Composable
fun NCardPreview(
    nCardMedia: NCardMedia,
    hasIcon: Boolean,
    hasTitle: Boolean,
    hasSubtitle: Boolean
) {
    Card(
        modifier = Modifier
            .padding(bottom = 2.dp) // Card の下端に padding で隙間を作らないと、Card の影が表示されない。
            .width(316.dp)
            .height(220.dp)
    ) {
        ConstraintLayout {
            val (
                n_card_media,
                icon,
                title,
                subtitle
            ) = createRefs()

            // タイトルとサブタイトルを隙間なく上下に並べるために Packed を使う。
            constrain(createVerticalChain(title, subtitle, chainStyle = ChainStyle.Packed)) {
                top.linkTo(n_card_media.bottom)
                bottom.linkTo(parent.bottom)
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .constrainAs(n_card_media) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                when (nCardMedia) {
                    NCardMedia.IMAGE -> Image(
                        painter = painterResource(id = R.drawable.sample),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                    NCardMedia.SOLID -> Spacer(
                        Modifier
                            .background(Color(0xff828282))
                            .fillMaxSize()
                    )
                    NCardMedia.MOVIE -> NFrameExoPlayer(
                        modifier = Modifier.fillMaxSize(),
                        uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                    )
                    NCardMedia.NONE -> {}
                }
            }

            if (hasIcon) {
                Image(
                    painter = painterResource(id = R.drawable.ic_n_card_head_icon),
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(icon) {
                            start.linkTo(parent.start, 16.dp)
                            top.linkTo(n_card_media.bottom)
                            bottom.linkTo(parent.bottom)
                        },
                    contentDescription = null
                )
            }

            if (hasTitle) {
                Text(
                    text = "カードタイトル",
                    modifier = Modifier
                        .padding(start = if (hasIcon) 8.dp else 16.dp)
                        .constrainAs(title) {
                            start.linkTo(icon.end)
                            bottom.linkTo(subtitle.top)
                        },
                    color = Gray500,
                    fontSize = 14.sp
                )
            }

            if (hasSubtitle) {
                Text(
                    text = "カードサブタイトル",
                    modifier = Modifier
                        .padding(start = if (hasIcon) 8.dp else 16.dp)
                        .constrainAs(subtitle) {
                            start.linkTo(icon.end)
                            top.linkTo(title.bottom)
                        },
                    color = Gray400,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNCardPreview() {
    NCardPreview(
        nCardMedia = NCardMedia.IMAGE,
        hasIcon = true,
        hasTitle = true,
        hasSubtitle = true
    )
}
