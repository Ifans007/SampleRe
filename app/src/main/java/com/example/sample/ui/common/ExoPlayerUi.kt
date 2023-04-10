package com.example.sample.ui.common

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.sample.R

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoPlayerUi(
    preview: String,
    hlsUri: String
) {

    Box {

        var isShowVideo by remember { mutableStateOf(false) }
        var isShowPreview by remember { mutableStateOf(true) }

        if (isShowVideo) {

            val context = LocalContext.current

            val exoPlayer = remember(context) {

                ExoPlayer.Builder(context).build().apply {

                    val mediaItem = MediaItem.Builder().setUri(hlsUri).build()

                    setMediaItem(mediaItem)

                    prepare()
                    playWhenReady = true
                    addListener(object : Player.Listener {

                        override fun onRenderedFirstFrame() {
                            super.onRenderedFirstFrame()
                            isShowPreview = false
                        }
                    })
                }
            }

            DisposableEffect(
                AndroidView(
                    factory = {
                        PlayerView(context).apply {
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                            player = exoPlayer
                            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        }
                    })
            ) {
                onDispose { exoPlayer.release() }
            }
        }

        if (isShowPreview) {

            Box {

                AsyncImage(
                    model = preview,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    isShowVideo = true
                                }
                            )
                        }
                )

                if (!isShowVideo)
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .size(size = 128.dp)
                            .align(alignment = Alignment.Center)
                    )

                if (isShowVideo)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(size = 128.dp)
                            .align(alignment = Alignment.Center)
                    )
            }
        }
    }
}