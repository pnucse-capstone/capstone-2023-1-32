package com.kick.npl.ui.map.model

import com.kick.npl.data.remote.dto.RouteUnitEnt
import com.kick.npl.model.ParkingLotData

data class SelectedParkingLotData(
    val parkingLotData: ParkingLotData,
    val routeFromCurrent: RouteUnitEnt?
)
