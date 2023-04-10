package com.example.sample.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sample.model.comment.CommentModel
import com.example.sample.ui.theme.RedditRUFirstTheme
import com.example.sample.ui.theme.Purple500

@Composable
fun CommentItem(
    comment: CommentModel,
    hasSubCommentList: Boolean = false,
    onClickSub: () -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
    ) {

        Text(
            text = comment.author,
            style = MaterialTheme.typography.labelLarge,
            modifier =
            Modifier
                .alpha(0.6f)
        )
        Text(
            text = comment.ups.toString(),
            modifier =
            Modifier
                .padding(bottom = 8.dp)
        )
        Text(
            text = comment.body,
            style = MaterialTheme.typography.bodyLarge,
        )
        IconButton(
            onClick = { onClickSub() },
            modifier = Modifier
                .background(
                    color = Purple500,
                    shape = RoundedCornerShape(16.dp)
                )
                .align(alignment = Alignment.End)
        ) {
            Icon(
                imageVector = if (hasSubCommentList) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentItemPreview() {

    RedditRUFirstTheme {
        CommentItem(
            comment = CommentModel.SAMPLE,
            onClickSub = {}
        )
    }
}