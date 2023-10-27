package com.kick.npl.ui.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kick.npl.ui.favorite.favoriteGraph
import com.kick.npl.ui.manage.managingGraph
import com.kick.npl.ui.map.mapGraph
import com.kick.npl.ui.parkinglot.parkingLotGraph
import com.kick.npl.ui.setting.settingGraph

@Composable
fun NPLNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) = NavHost(
    route = "root",
    modifier = modifier,
    navController = navController,
    startDestination = NPLBottomRoute.Map.route,
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
) {
    mapGraph(navController)
    favoriteGraph(navController)
    managingGraph(navController)
    settingGraph(navController)
    parkingLotGraph(navController)
}