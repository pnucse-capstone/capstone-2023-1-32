package com.kick.npl.data.repository

import com.kick.npl.data.model.ParkingLotEntity
import com.kick.npl.model.ParkingLotData

interface ParkingLotsRepository {
    suspend fun getParkingLot(id: String): ParkingLotEntity?
    suspend fun getAllParkingLots(): List<ParkingLotData>?
    suspend fun setParkingLot(parkingLotData: ParkingLotData)
    suspend fun toggleFavorite(id: String, favorite: Boolean)
    suspend fun deleteTestParkingLot(id: String)

    suspend fun setIsBlocked(id: String, isBlocked: Boolean)
    suspend fun setParkingLotData(parkingLotData: ParkingLotData)
}