@file:OptIn(ExperimentalMaterialApi::class)

package com.kick.npl.ui.manage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kick.npl.R
import com.kick.npl.ui.theme.Theme

@Composable
fun ManagingScreen(
    isLoading: Boolean,
    navigateToBarcodeScan: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background)
    ) {
        Text(
            text = "주차장 등록 및 관리",
            style = Theme.typo.title1B,
            color = Theme.colors.onBackground0,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Theme.colors.secondaryLine,
            thickness = 1.dp
        )

        AnimatedContent(targetState = isLoading, label = "") { loading ->
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
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        ParkingLotManageAddCard(navigateToBarcodeScan)
                    }
                }
            }
        }
    }
}

@Composable
fun ParkingLotManageCard(
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 4.dp,
        backgroundColor = Theme.colors.surface,
        onClick = onClick,
    ) {

    }
}

@Preview
@Composable
fun ParkingLotManageAddCard(
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        elevation = 4.dp,
        backgroundColor = Theme.colors.surface,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_24),
                contentDescription = null,
                tint = Theme.colors.primary,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "주차장 모듈 등록하기",
                style = Theme.typo.body,
                color = Theme.colors.onSurface40,
            )
        }
    }
}