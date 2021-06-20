package com.android.ae08bc4bf43145be1c0a32f0872b7f47.di

import android.content.Context
import androidx.room.Room
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.room.SpaceDao
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.room.SpaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object RoomModule {
    @Provides
    @Singleton
    fun provideCoinDb(@ApplicationContext context: Context): SpaceDatabase {
        return Room.databaseBuilder(
            context, SpaceDatabase::class.java,
            SpaceDatabase.DATABASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinDAO(coinDatabase: SpaceDatabase): SpaceDao {
        return coinDatabase.coinDao()
    }
}