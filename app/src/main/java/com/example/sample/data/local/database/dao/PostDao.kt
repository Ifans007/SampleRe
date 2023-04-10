package com.example.sample.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sample.data.local.database.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostList(postList: List<PostEntity>)

    @Query("SELECT * FROM PostEntity")
    fun getPostPagingSource(): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM PostEntity WHERE id=:id")
    fun getPostById(id: String): Flow<PostEntity>

    @Query("DELETE FROM PostEntity")
    suspend fun clearPosts()
}