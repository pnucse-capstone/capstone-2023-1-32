package com.kick.npl.data.di

import com.kick.npl.data.remote.api.MapsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideMapsApi(
        retrofit: Retrofit,
    ): MapsApi = retrofit.create(MapsApi::class.java)

}