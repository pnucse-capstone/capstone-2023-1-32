package com.kick.npl.ui.map

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.app.NPLBottomRoute
import com.kick.npl.ui.map.model.ParkingDateTime
import com.kick.npl.ui.parkinglot.navigateToParkingLotDetail

const val MAP_SCREEN = "mapScreen"

fun NavGraphBuilder.mapGraph(
    navController: NavController,
) {
    navigation(
        startDestination = MAP_SCREEN,
        route = NPLBottomRoute.Map.route
    ) {
        composable(
            route = MAP_SCREEN,
        ) {
            MapRoute(
                navigateToParkingLotDetail = navController::navigateToParkingLotDetail
            )
        }
    }
}

@Composable
fun MapRoute(
    navigateToParkingLotDetail: (ParkingLotData, ParkingDateTime) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAllParkingLots()
    }

    LaunchedEffect(viewModel.eventFlow) {
        viewModel.eventFlow.collect {
            when(it) {
                is MapViewModel.UiEvent.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is MapViewModel.UiEvent.NavigateToDetail -> {
                    navigateToParkingLotDetail(it.parkingLotData, it.parkingDateTime)
                }
                MapViewModel.UiEvent.FullSizeMap -> Unit
            }
        }
    }

    MapScreen(
        getAllParkingLots = viewModel::getAllParkingLots,
        parkingLotList = viewModel.parkingLotList,
        selectedParkingLot = viewModel.selectedParkingLot,
        cameraPositionState = viewModel.cameraPositionState,
        parkingDateTime = viewModel.parkingDateTime,
        onParkingLotMarkerClicked = viewModel::onMarkerClicked,
        onMarkerUnselected = viewModel::onMarkerUnselected,
        onLocationChange = viewModel::onLocationChange,
        onParkingDateTimeChanged = viewModel::onParkingDateTimeChanged,
        onClickFavorite = viewModel::onClickFavorite,
        onClickParkingLotCard = viewModel::onClickParkingLotCard,
    )
}
