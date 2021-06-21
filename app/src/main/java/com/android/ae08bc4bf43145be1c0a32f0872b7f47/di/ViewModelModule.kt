package com.android.ae08bc4bf43145be1c0a32f0872b7f47.di

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.repo.SpaceRepo
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase.SpaceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideUseCase(
            spaceRepo: SpaceRepo,
    ): SpaceUseCase {
        return SpaceUseCase(spaceRepo)
    }
}