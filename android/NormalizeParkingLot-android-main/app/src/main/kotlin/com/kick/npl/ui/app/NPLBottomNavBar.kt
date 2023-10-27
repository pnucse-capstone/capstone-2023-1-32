package com.kick.npl.ui.app

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kick.npl.ui.map.MAP_SCREEN
import com.kick.npl.ui.theme.Theme

@Composable
fun NPLBottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) = NavigationBar(
    modifier = modifier,
    tonalElevation = 32.dp,
    containerColor = Theme.colors.surface,
    contentColor = Theme.colors.onSurface0,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NPLBottomRoute.entries.forEachIndexed { index, item ->
        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = item.iconId),
                    contentDescription = item.title
                )
            },
            label = { Text(item.title) },
            selected = selected,
            onClick = {

                if(selected && item.route == NPLBottomRoute.Map.route) {
//                    val startDestination = navController.graph.findStartDestination().route?
//                    startDestination?.let { navController.navigate(it) }
                    navController.popBackStack()
                }
                navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Theme.colors.graphSafety90,
                selectedIconColor = Theme.colors.onBackground0,
                selectedTextColor = Theme.colors.onBackground0,
                unselectedIconColor = Theme.colors.onSurface40,
                unselectedTextColor = Theme.colors.onSurface40,
            )
        )
    }
}