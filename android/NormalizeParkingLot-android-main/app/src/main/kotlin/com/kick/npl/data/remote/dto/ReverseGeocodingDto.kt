package com.kick.npl.data.remote.dto

data class ReverseGeocodingDto(
    val results: List<Result>,
    val status: Status
)