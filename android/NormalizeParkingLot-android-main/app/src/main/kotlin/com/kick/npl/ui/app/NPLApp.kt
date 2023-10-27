package com.kick.npl.ui.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kick.npl.ui.theme.NPLTheme
import com.kick.npl.ui.theme.Theme

@Composable
fun NPLApp(
    appState: NPLAppState = rememberNPLAppState(),
) {
    NPLTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if(appState.shouldShowBar) {
                    Column {
                        HorizontalDivider(color = Theme.colors.secondaryLine)
                        NPLBottomNavBar(navController = appState.navController)
                    }
                }
            },
            containerColor = Theme.colors.background,
            contentColor = Theme.colors.onBackground0
        ) { contentPadding ->
            NPLNavHost(
                navController = appState.navController,
                modifier = Modifier.padding(contentPadding),
            )
        }
    }
}
