package com.example.sample.domain.type.base

interface BaseType {
    fun typeString(): String?
    fun typeInt(): Int?
    fun <T> returnUnknown(): T where T : Enum<T>, T : BaseType
}