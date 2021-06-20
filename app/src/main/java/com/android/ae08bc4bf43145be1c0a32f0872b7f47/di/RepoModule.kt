package com.android.ae08bc4bf43145be1c0a32f0872b7f47.di

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.network.SpaceApi
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.repo.SpaceRepo
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.room.SpaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {


    @Provides
    @Singleton
    fun providePortfolioRepo(
        ioDispatcher: CoroutineDispatcher,
        spaceDao: SpaceDao,
        api: SpaceApi,

        ): SpaceRepo {
        return SpaceRepo(ioDispatcher, spaceDao, api)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}







