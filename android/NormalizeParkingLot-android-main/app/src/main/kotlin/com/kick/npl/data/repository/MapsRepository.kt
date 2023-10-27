package com.kick.npl.data.repository

import com.kick.npl.data.model.LngLat
import com.kick.npl.data.remote.dto.RouteUnitEnt
import com.naver.maps.geometry.LatLng

interface MapsRepository {
    suspend fun getMinifiedAddress(latlng: LatLng): Result<String>
    suspend fun getDrivingRoute(start: LatLng, goal: LatLng, waypoints: List<LatLng>) : Result<RouteUnitEnt>
}