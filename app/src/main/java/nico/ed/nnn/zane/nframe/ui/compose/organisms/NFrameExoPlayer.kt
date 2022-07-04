package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun NFrameExoPlayer(uri: String) {
    AndroidView(
        factory = {
            StyledPlayerView(it).apply {
                player = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(MediaItem.fromUri(uri))
                    resizeMode = RESIZE_MODE_FILL
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewNFrameExoPlayer() {
    NFrameExoPlayer(uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
}
