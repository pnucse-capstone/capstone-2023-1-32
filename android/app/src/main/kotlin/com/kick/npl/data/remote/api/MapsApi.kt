package com.kick.npl.data.remote.api

import com.kick.npl.data.remote.dto.DrivingRoute
import com.kick.npl.data.remote.dto.ReverseGeocodingDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApi {

    @GET("map-reversegeocode/v2/gc")
    suspend fun getReverseGeocoding(
        @Query("coords") coords: String,
        @Query("output") output: String,
    ): Response<ReverseGeocodingDto>

    @GET("/map-direction/v1/driving")
    suspend fun getDrivingRoute(
        @Query("start") start: String,
        @Query("goal") goal: String,
        @Query("waypoints") waypoints: String,
    ): Response<DrivingRoute>
}