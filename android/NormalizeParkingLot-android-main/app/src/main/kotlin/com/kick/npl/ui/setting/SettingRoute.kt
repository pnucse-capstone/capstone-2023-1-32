package com.kick.npl.ui.setting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kick.npl.ui.app.NPLBottomRoute
import com.kick.npl.ui.theme.Theme

const val SETTING_SCREEN = "settingScreen"

fun NavGraphBuilder.settingGraph(
    navController: NavController,
) {
    navigation(
        startDestination = SETTING_SCREEN,
        route = NPLBottomRoute.Setting.route
    ) {
        composable(
            route = SETTING_SCREEN,
        ) {
            SettingRoute()
        }
    }
}

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel.toastMessage) {
        viewModel.toastMessage.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    if (viewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        SettingScreen(
            parkingLotData = viewModel.parkingLotData,
            getParkingLotData = viewModel::getTestParkingLot,
            updateField = viewModel::updateField,
            mockTestData = viewModel::mockData,
            deleteTestData = viewModel::deleteAllTestParkingLots,
            logout = viewModel::logout,
        )
    }
}