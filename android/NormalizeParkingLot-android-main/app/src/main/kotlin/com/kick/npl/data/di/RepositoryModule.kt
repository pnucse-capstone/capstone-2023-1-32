package com.kick.npl.data.di

import com.kick.npl.data.repository.MapsRepository
import com.kick.npl.data.repository.MapsRepositoryImpl
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.data.repository.ParkingLotsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindMapsRepository(
        mapsRepository: MapsRepositoryImpl,
    ): MapsRepository

    @Binds
    abstract fun bindParkingLotsRepository(
        parkingLotsRepository: ParkingLotsRepositoryImpl,
    ): ParkingLotsRepository

}