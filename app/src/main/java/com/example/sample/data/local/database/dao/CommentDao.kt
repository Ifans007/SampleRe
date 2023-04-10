package com.example.sample.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sample.data.local.database.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommentList(commentList: List<CommentEntity>)

    @Query("SELECT * FROM CommentEntity WHERE parentId = :id ORDER BY ups DESC")
    fun getCommentListByParentId(id: String): List<CommentEntity>
}