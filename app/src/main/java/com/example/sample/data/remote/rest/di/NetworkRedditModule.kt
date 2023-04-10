package com.example.sample.data.remote.rest.di

import com.example.sample.data.remote.rest.api.CommentApi
import com.example.sample.data.remote.rest.api.PostApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkRedditModule {

    @Singleton
    @Provides
    fun providePostApi(
        @RetrofitQualifier(RetrofitType.REDDIT)
        retrofit: Retrofit
    ): PostApi = retrofit.create(PostApi::class.java)

    @Singleton
    @Provides
    fun provideCommentApi(
        @RetrofitQualifier(RetrofitType.REDDIT)
        retrofit: Retrofit
    ): CommentApi = retrofit.create(CommentApi::class.java)

}