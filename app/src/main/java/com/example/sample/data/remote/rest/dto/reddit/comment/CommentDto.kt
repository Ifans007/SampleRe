package com.example.sample.data.remote.rest.dto.reddit.comment

import com.example.sample.data.local.database.entity.CommentEntity
import com.example.sample.data.remote.rest.dto.reddit.common.ContentType
import com.example.sample.data.remote.rest.dto.reddit.common.SCommonDataDto
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class CommentDto(
    val id: String = "",
    @SerialName(value = "parent_id")
    val parentId: String = "",
    val author: String = "",
    val created: Double = -1.0,
    val ups: Int = -1,
    val body: String = "",
    @Serializable(with = CommentRepliesSerializer::class)
    val replies: SCommonDataDto? = null
) {

}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = SCommonDataDto::class)
object CommentRepliesSerializer : KSerializer<SCommonDataDto?> {

    override val descriptor: SerialDescriptor = SCommonDataDto.serializer().descriptor

    override fun deserialize(decoder: Decoder): SCommonDataDto? {

        return try {
            decoder.decodeSerializableValue(SCommonDataDto.serializer())
        } catch (e: Exception) {
            null
        }
    }
}

fun CommentDto.toEntity(): CommentEntity {

    return CommentEntity(
        id = "${ContentType.COMMENT.type}$id",
        parentId = parentId,
        author = author,
        created = created,
        ups = ups,
        body = body
    )
}