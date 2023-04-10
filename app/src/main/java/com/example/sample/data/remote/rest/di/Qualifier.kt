package com.example.sample.data.remote.rest.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitQualifier(@Suppress("unused") val retrofit: RetrofitType)

enum class RetrofitType(val api: String) {
    REDDIT("https://www.reddit.com/")
}
