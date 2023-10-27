package com.kick.npl.ui.manage

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.app.NPLBottomRoute
import com.kick.npl.ui.parkinglot.PARKING_LOT_DETAIL_KEY

const val MANAGING_ROUTE = "managingStart"
const val BARCODE_SCAN_ROUTE = "barcodeScan"
const val ADD_PARKING_LOT_ROUTE = "addParkingLot"

fun NavGraphBuilder.managingGraph(
    navController: NavController,
) {
    navigation(
        startDestination = MANAGING_ROUTE,
        route = NPLBottomRoute.Managing.route,
    ) {
        composable(
            route = MANAGING_ROUTE,
        ) {
            ManagingRoute(
                navigateToBarcodeScan = { navController.navigate(BARCODE_SCAN_ROUTE) }
            )
        }
        composable(
            route = BARCODE_SCAN_ROUTE,
        ) {
            BarcodeScanningScreen(navController)
        }
        composable(
            route = ADD_PARKING_LOT_ROUTE,
        ) {
            val barcode = it.savedStateHandle.get<String>("barcode")
            AddParkingLotRoute(
                barcode = barcode,
                onClickUp = {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun ManagingRoute(
    navigateToBarcodeScan: () -> Unit,
    viewModel: ManagingViewModel = hiltViewModel(),
) {
    ManagingScreen(
        isLoading = false,
        navigateToBarcodeScan = navigateToBarcodeScan,
    )
}

