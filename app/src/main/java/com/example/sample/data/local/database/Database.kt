package com.example.sample.data.local.database

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.sample.data.local.database.dao.CommentDao
import com.example.sample.data.local.database.dao.PostDao
import com.example.sample.data.local.database.entity.CommentEntity
import com.example.sample.data.local.database.entity.PostEntity


@Database(
    entities = [
        PostEntity::class,
        CommentEntity::class
               ],
    version = 1,
    exportSchema = true
)

abstract class Database : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}