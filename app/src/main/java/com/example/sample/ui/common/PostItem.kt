package com.example.sample.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sample.R
import com.example.sample.domain.type.reddit.PostHintEnumType
import com.example.sample.model.post.PostModel
import com.example.sample.ui.theme.RedditRUFirstTheme

@Composable
fun PostItem(post: PostModel) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp,)
        ) {

            Text(
                text = post.subreddit,
                style = MaterialTheme.typography.labelLarge,
                modifier =
                Modifier
                    .alpha(0.6f)
            )
            Text(
                text = post.ups.toString(),
            )
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge
            )
            if (post.selftext.isNotBlank()) {
                ExpandableText(
                    text = post.selftext,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (post.postHint == PostHintEnumType.IMAGE || post.postHint == PostHintEnumType.REDDIT_VIDEO) {

                if (post.isVideo || post.isGif) {

                    ExoPlayerUi(
                        preview = post.image ?: "",
                        hlsUri = post.hlsUrl
                    )
                } else {

                    AsyncImage(
                        model = post.image,
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            } else {

                Text(text = "---> This Gallery or Link <---")
            }
        }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {

    RedditRUFirstTheme {
        PostItem(
            post = PostModel.SAMPLE
        )
    }
}