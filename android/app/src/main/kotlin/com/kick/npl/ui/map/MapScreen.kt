package com.kick.npl.ui.map

import android.location.Location
import android.util.Log
import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.kick.npl.R
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.common.BottomSheet
import com.kick.npl.ui.common.ParkingLotCard
import com.kick.npl.ui.map.component.DateTimePickerBottomSheet
import com.kick.npl.ui.map.component.TimeRangeBar
import com.kick.npl.ui.map.model.ParkingDateTime
import com.kick.npl.ui.map.model.SelectedParkingLotData
import com.kick.npl.ui.theme.NPLTheme
import com.kick.npl.ui.theme.Theme
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerDefaults
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun MapScreen(
    getAllParkingLots: () -> Unit,
    parkingLotList: List<ParkingLotData>,
    selectedParkingLot: SelectedParkingLotData? = null,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    parkingDateTime: ParkingDateTime,
    onMarkerUnselected: () -> Unit = {},
    onParkingLotMarkerClicked: (String) -> Unit = {},
    onLocationChange: (Location) -> Unit = {},
    onParkingDateTimeChanged: (ParkingDateTime) -> Unit = {},
    onClickFavorite: (String) -> Unit = {},
    onClickParkingLotCard: (String) -> Unit = {},
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    val scope = rememberCoroutineScope()
    var isTimePickerVisible by remember { mutableStateOf(false) }

    DateTimePickerBottomSheet(
        enabled = isTimePickerVisible,
        startTime = parkingDateTime.startTime,
        endTime = parkingDateTime.endTime,
        onDismissRequest = { isTimePickerVisible = false },
        onClickConfirm = { startTime, endTime ->
            isTimePickerVisible = false
            onParkingDateTimeChanged(ParkingDateTime(startTime, endTime))
            getAllParkingLots()
        },
    )

    TimeRangeBar(
        startTime = parkingDateTime.startTime,
        endTime = parkingDateTime.endTime,
        onClickChangeButton = { isTimePickerVisible = true }
    )

    BottomSheet(
        onExpanded = onMarkerUnselected,
        sheetContent = { hide ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 100.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(parkingLotList) { parkingLotData ->
                    ParkingLotCard(
                        parkingLotData = parkingLotData,
                        haveBorder = true,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        onClickFavorite = { onClickFavorite(parkingLotData.id) },
                        onClickCard = {
                            onParkingLotMarkerClicked(parkingLotData.id)
                            scope.launch { hide() }
                        }
                    )
                }
            }
        },
        content = {
            MapScreenContent(
                parkingLotList,
                selectedParkingLot,
                cameraPositionState,
                onParkingLotMarkerClicked,
                onMarkerUnselected,
                onLocationChange,
                onClickFavorite,
                onClickParkingLotCard
            )
        }
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreenContent(
    parkingLotList: List<ParkingLotData>,
    selectedParkingLot: SelectedParkingLotData?,
    cameraPositionState: CameraPositionState,
    onParkingLotMarkerClicked: (String) -> Unit,
    onMarkerUnselected: () -> Unit,
    onLocationChange: (Location) -> Unit,
    onClickFavorite: (String) -> Unit = {},
    onClickParkingLotCard: (String) -> Unit = {},
) {
    Box {
        AnimatedVisibility(
            visible = selectedParkingLot != null,
            enter = slideInVertically { it / 2 } + fadeIn(),
            exit = slideOutVertically { it / 2 } + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 85.dp)
        ) {
            val id = selectedParkingLot?.parkingLotData?.id
            ParkingLotCard(
                selectedParkingLot?.parkingLotData,
                modifier = Modifier.padding(horizontal = 12.dp),
                onClickFavorite = { if(id != null) onClickFavorite(id) },
                onClickCard = { if(id != null) onClickParkingLotCard(id) }
            )
        }

        NaverMap(
            modifier = Modifier
                .zIndex(-1f)
                .padding(bottom = 56.dp)
                .fillMaxSize(),
            properties = MapProperties(
                mapType = MapType.Navi,
                maxZoom = 18.0,
                minZoom = 5.0,
                isBuildingLayerGroupEnabled = false,
                isNightModeEnabled = isSystemInDarkTheme(),
                locationTrackingMode = LocationTrackingMode.Follow
            ),
            uiSettings = MapUiSettings(
                isTiltGesturesEnabled = false,
                isCompassEnabled = false,
                isZoomControlEnabled = false,
                isRotateGesturesEnabled = false,
                isScaleBarEnabled = false,
                isLocationButtonEnabled = true,
                isLogoClickEnabled = false,
                logoGravity = Gravity.TOP or Gravity.START
            ),
            locationSource = rememberFusedLocationSource(),
            cameraPositionState = cameraPositionState,
            onLocationChange = onLocationChange,
            onMapClick = { _, _ -> onMarkerUnselected() }
        ) {
            val isOverlayVisible by remember(selectedParkingLot) {
                derivedStateOf {
                    selectedParkingLot?.routeFromCurrent != null
                }
            }
            val animatedMarkerSize by animateFloatAsState(
                targetValue = if (isOverlayVisible) 1f else 0.5f, label = ""
            )
            val animatedPathProgress by animateFloatAsState(
                label = "",
                targetValue = if (isOverlayVisible) 1f else 0.1f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            )

            if (isOverlayVisible) {
                PathOverlay(
                    coords = selectedParkingLot!!.routeFromCurrent!!.getPathList().let {
                        it.subList(0, (it.lastIndex * animatedPathProgress).toInt())
                    },
                    width = (animatedMarkerSize * 10).dp,
                    outlineWidth = 1.dp,
                    color = Theme.colors.graphSafety40,
                    patternImage = OverlayImage.fromResource(R.drawable.ic_double_arrow_navigate),
                    patternInterval = 80.dp,
                    globalZIndex = 1
                )
                Marker(
                    width = (animatedMarkerSize * 79).dp,
                    height = (animatedMarkerSize * 53).dp,
                    zIndex = 2,
                    icon = OverlayImage.fromResource(R.drawable.ic_marker_current),
                    iconTintColor = Theme.colors.primary,
                    state = MarkerState(
                        position = selectedParkingLot.routeFromCurrent!!.summary.getStartLatLng()
                    ),
                )
            }

            parkingLotList.forEach { parkingLotData ->
                val isSelected = selectedParkingLot?.parkingLotData?.id == parkingLotData.id
                val animatedSize by animateFloatAsState(
                    label = "marker size multiplier",
                    targetValue = if(isSelected) 1.2f else 0.9f
                )

                Marker(
                    width = (animatedSize * 44).dp,
                    height = (animatedSize * 55).dp,
                    zIndex = if(isSelected) 2 else 0,
                    icon = OverlayImage.fromResource(
                        when(isSelected) {
                            true -> R.drawable.ic_marker_selected
                            false -> R.drawable.ic_marker_circle
                        }
                    ),
                    iconTintColor = Theme.colors.primary,
                    state = MarkerState(parkingLotData.latLng),
                    captionMinZoom = 11.0,
                    subCaptionMinZoom = 11.0,
                    captionText = parkingLotData.name,
                    onClick = {
                        onParkingLotMarkerClicked(parkingLotData.id)
                        true
                    }
                )
            }
        }
    }
}
