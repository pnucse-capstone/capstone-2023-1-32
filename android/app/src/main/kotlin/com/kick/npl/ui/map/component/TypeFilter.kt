package com.kick.npl.ui.map.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kick.npl.model.ParkingLotType
import com.kick.npl.ui.theme.Theme

@Composable
fun FilterBar(
    filterEnable: Map<ParkingLotType, Boolean>,
    onFilterSelected: (ParkingLotType) -> Unit = {},
) = Column(modifier = Modifier.fillMaxWidth(),) {
    Row(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxWidth()
            .padding(start = 8.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        ParkingLotType.values().forEach {
            FilterCard(
                it.name,
                filterEnable[it]!!,
            ) { onFilterSelected(it) }
        }
    }
}

@Composable
fun FilterCard(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) = Surface(
    shape = RoundedCornerShape(24.dp),
    contentColor = if (selected) Theme.colors.onPrimaryContainer else Theme.colors.onSurface40,
    color = if(selected) Theme.colors.primary else Theme.colors.onPrimaryContainer,
    border = BorderStroke(1.dp, Theme.colors.onSurface40).takeIf { !selected },
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp)
            .padding(vertical = 2.dp)
            .padding(4.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
        )
    }
}