package com.kick.npl.ui.favorite

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.common.ParkingLotCard
import com.kick.npl.ui.theme.Theme


@Composable
fun FavoriteScreen(
    isLoading: Boolean,
    parkingLotList: List<ParkingLotData>,
    onClickFavorite: (ParkingLotData) -> Unit,
    onClickCard: (ParkingLotData) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background)
    ) {
        Text(
            text = "즐겨찾는 주차장",
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(parkingLotList) { parkingLotData ->
                        ParkingLotCard(
                            parkingLotData = parkingLotData,
                            haveBorder = true,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            onClickFavorite = { onClickFavorite(parkingLotData) },
                            onClickCard = { onClickCard(parkingLotData) }
                        )
                    }
                }
            }
        }
    }
}
