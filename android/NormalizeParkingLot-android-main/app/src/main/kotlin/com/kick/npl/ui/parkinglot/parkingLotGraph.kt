package com.kick.npl.ui.parkinglot

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.map.model.ParkingDateTime
import com.kick.npl.ui.theme.Theme
import kotlinx.coroutines.delay

const val PARKING_LOT_DETAIL = "parkingLotDetail"
const val PARKING_LOT_DETAIL_KEY = "parkingLotDetailKey"
const val PARKING_DATE_TIME_KEY = "parkingDateTimeKey"

fun NavGraphBuilder.parkingLotGraph(
    navController: NavController,
) {
    composable(
        route = PARKING_LOT_DETAIL,
    ) {
        val parkingLotData = it.savedStateHandle.get<ParkingLotData>(PARKING_LOT_DETAIL_KEY)
        val parkingDateTime = it.savedStateHandle.get<ParkingDateTime?>(PARKING_DATE_TIME_KEY)
        val popUp = { navController.popBackStack() }

        var delay by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            delay = true
            delay(500)
            delay = false
        }
        AnimatedContent(targetState = delay, label = "") { loading ->
            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Theme.colors.primary
                    )
                }
            } else {
                ParkingLotDetailScreen(
                    parkingLotData = parkingLotData ?: run { popUp(); return@AnimatedContent },
                    parkingDateTime = parkingDateTime,
                    onClickClose = { navController.popBackStack() },
                )
            }
        }
    }
}

fun NavController.navigateToParkingLotDetail(
    parkingLotData: ParkingLotData,
    parkingDateTime: ParkingDateTime? = null,
) {
    navigate(PARKING_LOT_DETAIL)
    currentBackStackEntry?.savedStateHandle?.set(PARKING_LOT_DETAIL_KEY, parkingLotData)
    currentBackStackEntry?.savedStateHandle?.set(PARKING_DATE_TIME_KEY, parkingDateTime)
}