package com.example.sample.domain.type.reddit

import com.example.sample.domain.type.base.BaseType

enum class PostHintEnumType(
    val type: String
) : BaseType {

    TEXT("self"),
    IMAGE("image"),
    REDDIT_VIDEO("hosted:video"),
    NOT_REDDIT_VIDEO("rich:video"),
    LINK("link"),
    UNKNOWN("unknown");

    override fun typeString(): String {
        return type
    }

    override fun typeInt(): Int? {
        return null
    }

    override fun <T> returnUnknown(): T where T : Enum<T>, T : BaseType {
        return UNKNOWN as T
    }
}