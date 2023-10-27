package com.kick.npl.data.model

data class LngLat(
    val longitude: Double,
    val latitude: Double
) {
    fun toCoordString() : String = "$longitude,$latitude"
}
