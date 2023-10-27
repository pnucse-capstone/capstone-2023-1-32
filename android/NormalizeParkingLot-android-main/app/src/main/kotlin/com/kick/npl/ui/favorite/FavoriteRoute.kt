package com.kick.npl.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.app.NPLBottomRoute
import com.kick.npl.ui.parkinglot.navigateToParkingLotDetail

fun NavGraphBuilder.favoriteGraph(navController: NavController) {
    composable(NPLBottomRoute.Favorite.route) {
        FavoriteRoute(
            onClickCard = { parkingLotData ->
                navController.navigateToParkingLotDetail(parkingLotData)
            }
        )
    }
}

@Composable
fun FavoriteRoute(
    onClickCard: (ParkingLotData) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getFavoriteParkingLots()
    }

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    FavoriteScreen(
        isLoading = isLoading,
        parkingLotList = viewModel.favoriteParkingLots,
        onClickFavorite = viewModel::onClickFavorite,
        onClickCard = onClickCard,
    )
}