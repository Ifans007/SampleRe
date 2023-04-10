package com.example.sample.data.remote.rest.dto.reddit.common

import com.example.sample.data.remote.rest.dto.reddit.comment.CommentDto
import com.example.sample.data.remote.rest.dto.reddit.comment.MoreDto
import com.example.sample.data.remote.rest.dto.reddit.post.PostDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@OptIn(ExperimentalSerializationApi::class)
val module = SerializersModule {
    polymorphic(SCommonDataDto::class) {
        subclass(SCommonDataDto.Listing::class)
        subclass(SCommonDataDto.Post::class)
        subclass(SCommonDataDto.Comment::class)
        subclass(SCommonDataDto.More::class)
        defaultDeserializer { SCommonDataDto.Unknown.serializer() }
    }
}

enum class ContentType(val type: String) {
    LISTING("Listing"),
    POST("t3_"),
    COMMENT("t1_"),
    MORE("more");
}

@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("kind")
@Serializable
sealed class SCommonDataDto {

    @Serializable
    data class Unknown(
        val kind: String
    ) :
        SCommonDataDto()

    @Serializable
    @SerialName("Listing")
    data class Listing(
        val data: DataListDto
    ) :
        SCommonDataDto()

    @Serializable
    @SerialName("t3")
    data class Post(
        val data: PostDto
    ) :
        SCommonDataDto()

    @Serializable
    @SerialName("t1")
    data class Comment(
        val data: CommentDto
    ) :
        SCommonDataDto()

    @Serializable
    @SerialName("more")
    data class More(
        val data: MoreDto
    ) :
        SCommonDataDto()
}

fun SCommonDataDto.findComments(): List<SCommonDataDto.Comment> {

    val commentList = mutableListOf<SCommonDataDto.Comment>()

    if (this is SCommonDataDto.Listing) {
        commentList.addAll(elements = this.findComments())
    }

    return commentList
}

private fun SCommonDataDto.Listing.findComments(): List<SCommonDataDto.Comment> {

    val commentList = mutableListOf<SCommonDataDto.Comment>()

    data.children.forEach {
        if (it is SCommonDataDto.Comment) {
            commentList.addAll(it.findComments())
        }
    }

    return commentList
}

private fun SCommonDataDto.Comment.findComments(): List<SCommonDataDto.Comment> {

    val commentList = mutableListOf<SCommonDataDto.Comment>()

    if (data.replies is SCommonDataDto.Listing) {
        commentList.add(this)
        commentList.addAll(data.replies.findComments())
    }

    return commentList

}

