package com.kick.npl.data.remote.dto

import com.naver.maps.geometry.LatLng

data class RouteUnitEnt(
    val summary: Summary = Summary(),
    val path: List<List<Double>> = emptyList(),
) {
    fun getPathList() : List<LatLng> = path.map { LatLng(it[1], it[0]) }
}