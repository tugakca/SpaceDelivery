package com.android.ae08bc4bf43145be1c0a32f0872b7f47.di

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.network.SpaceApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
                .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideSpaceService(retrofit: Retrofit.Builder): SpaceApi {
        return retrofit
                .build()
                .create(SpaceApi::class.java)
    }
}
