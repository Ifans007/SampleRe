package com.example.sample.data.local.database.di

import com.example.sample.data.local.database.Database
import com.example.sample.data.local.database.dao.CommentDao
import com.example.sample.data.local.database.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesPostDao(
        database: Database,
    ): PostDao = database.postDao()

    @Provides
    fun providesCommentDao(
        database: Database,
    ): CommentDao = database.commentDao()
}