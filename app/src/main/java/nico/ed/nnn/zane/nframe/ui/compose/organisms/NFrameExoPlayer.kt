package nico.ed.nnn.zane.nframe.ui.compose.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun NFrameExoPlayer(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    uri: String
) {
    lateinit var styledPlayerView: StyledPlayerView

    AndroidView(
        factory = {
            StyledPlayerView(it).apply {
                styledPlayerView = this
                player = ExoPlayer.Builder(it).build().apply {
                    setMediaItem(MediaItem.fromUri(uri))
                    playWhenReady = true // 自動再生に必要
                    prepare() // 自動再生に必要
                }
                resizeMode = RESIZE_MODE_FILL
                useController = false
            }
        }
    )

    DisposableEffect(lifecycleOwner) {
        onDispose {
            styledPlayerView.player?.release()
        }
    }
}

@Preview
@Composable
private fun PreviewNFrameExoPlayer() {
    NFrameExoPlayer(uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
}
