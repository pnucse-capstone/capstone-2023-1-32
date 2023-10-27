package com.kick.npl.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kick.npl.ui.manage.ADD_PARKING_LOT_ROUTE
import com.kick.npl.ui.manage.BARCODE_SCAN_ROUTE
import com.kick.npl.ui.parkinglot.PARKING_LOT_DETAIL
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberNPLAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(navController, coroutineScope) {
    NPLAppState(navController, coroutineScope)
}

@Stable
class NPLAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        get() = navController.currentDestination

    private val bottomBarTabs = NPLBottomRoute.entries.map { it.route }
//    val shouldShowBar: Boolean
//        @Composable get() = navController
//            .currentBackStackEntryAsState().value?.destination?.route in bottomBarTabs

    val shouldShowBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route !in listOf(
                PARKING_LOT_DETAIL,
                BARCODE_SCAN_ROUTE,
                ADD_PARKING_LOT_ROUTE,

            )

    fun upPress() = navController.navigateUp()
}