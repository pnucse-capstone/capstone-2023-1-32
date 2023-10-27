package com.kick.npl.data.remote.dto

data class ResponsePositionFormat(
    val location: List<Double> = emptyList(),
) {
    fun getLongitude() = if(location.isNotEmpty()) location.first() else 0.0
    fun getLatitude() = if(location.isNotEmpty()) location[1] else 0.0
}