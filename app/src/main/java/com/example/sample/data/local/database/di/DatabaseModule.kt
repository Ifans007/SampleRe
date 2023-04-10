package com.example.sample.data.local.database.di

import android.content.Context
import androidx.room.Room
import com.example.sample.data.local.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): Database {

        val databaseName = "sample-database"

        return Room.databaseBuilder(
            context,
            Database::class.java,
            databaseName,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}