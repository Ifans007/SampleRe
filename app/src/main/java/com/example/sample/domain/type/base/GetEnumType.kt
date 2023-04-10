package com.example.sample.domain.type.base

object GetEnumType {

    inline fun <reified T> getType(type: String?): T where T : Enum<T>, T : BaseType {
        enumValues<T>().forEach {
            if (it.typeString() == null) return it.returnUnknown()
            if (it.typeString() == type) return it
        }
        return enumValues<T>().first().returnUnknown()
    }

    inline fun <reified T> getType(type: Int?): T where T : Enum<T>, T : BaseType {
        enumValues<T>().forEach {
            if (it.typeInt() == null) return it.returnUnknown()
            if (it.typeInt() == type) return it
        }
        return enumValues<T>().first().returnUnknown()
    }
}