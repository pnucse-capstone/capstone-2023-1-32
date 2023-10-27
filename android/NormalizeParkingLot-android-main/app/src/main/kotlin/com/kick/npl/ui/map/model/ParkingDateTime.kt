package com.kick.npl.ui.map.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ParkingDateTime(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
) : Parcelable
