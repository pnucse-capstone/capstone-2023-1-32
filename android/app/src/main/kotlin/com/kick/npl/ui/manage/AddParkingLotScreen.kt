package com.kick.npl.ui.manage

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.firestore.GeoPoint
import com.kick.npl.R
import com.kick.npl.data.remote.dto.Center
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.common.OutlinedBox
import com.kick.npl.ui.common.OutlinedTextField
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState


@Composable
fun AddParkingLotRoute(
    barcode: String?,
    onClickUp: () -> Unit = {},
    viewModel: AddParkingLotViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    BackHandler { onClickUp() }
    LaunchedEffect(Unit) {
        viewModel.parkingLotData.value =
            viewModel.parkingLotData.value.copy(id = barcode)
    }
    LaunchedEffect(viewModel.result) {
        viewModel.result.collect {
            when (it) {
                is AddParkingLotResult.Success -> {
                    Toast.makeText(
                        context,
                        "주차장 등록에 성공했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClickUp()
                }

                is AddParkingLotResult.Failure -> {
                    Toast.makeText(
                        context,
                        "주차장 등록에 실패했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    AddParkingLotScreen(
        isLoading = viewModel.isLoading,
        onClickUp = onClickUp,
        parkingLotData = viewModel.parkingLotData,
        onLocationChange = { viewModel.getAddressName(it) },
        temporalAddressName = viewModel.temporalAddressName,
        onClickRegister = { viewModel.registerParkingLot() }
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun AddParkingLotScreen(
    isLoading: Boolean,
    parkingLotData: MutableState<AddParkingLotUiState>,
    onClickUp: () -> Unit = {},
    onLocationChange: (LatLng) -> Unit = {},
    temporalAddressName: MutableState<String?>,
    onClickRegister: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = onClickUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = Theme.colors.onBackground0
                )
            }
            Text(
                text = "주차장 모듈 등록",
                style = Theme.typo.title1B,
                color = Theme.colors.onBackground0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

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
                var isAddressSelectorExpanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        title = "주차장 이름",
                        hint = "등록할 주차장 모듈의 이름을 입력해주세요",
                        text = parkingLotData.value.title ?: "",
                        onValueChange = {
                            parkingLotData.value = parkingLotData.value.copy(title = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    OutlinedTextField(
                        title = "주차장 설명",
                        hint = "등록할 주차장에 대한 설명을 적어주세요",
                        text = parkingLotData.value.description ?: "",
                        onValueChange = {
                            parkingLotData.value = parkingLotData.value.copy(description = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    Column {
                        TextFieldLabel(text = "주차장 주소")
                        Spacer(Modifier.height(2.dp))
                        Row(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Theme.colors.secondaryLine,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(16.dp)
                                .noRippleClickable {
                                    isAddressSelectorExpanded = !isAddressSelectorExpanded
                                    if (isAddressSelectorExpanded) {
                                        temporalAddressName.value =
                                            parkingLotData.value.address ?: ""
                                    }
                                }
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = if (!isAddressSelectorExpanded) {
                                    parkingLotData.value.address ?: ""
                                } else {
                                    temporalAddressName.value ?: ""
                                },
                                modifier = Modifier,
                                style = Theme.typo.body,
                                color = Theme.colors.onBackground0
                            )
                            Spacer(Modifier.weight(1f))
                            Icon(
                                imageVector = if (isAddressSelectorExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = "back",
                                tint = Theme.colors.onBackground0
                            )
                        }

                        if (isAddressSelectorExpanded) {
                            Spacer(Modifier.height(2.dp))
                            Column(
                                Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Theme.colors.secondaryLine,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                val cameraPositionState = rememberCameraPositionState()
                                LaunchedEffect(cameraPositionState.position) {
                                    if (cameraPositionState.isMoving.not()) {
                                        onLocationChange(
                                            LatLng(
                                                cameraPositionState.position.target.latitude,
                                                cameraPositionState.position.target.longitude
                                            )
                                        )
                                    }
                                }
                                NaverMap(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(4.dp))
                                        .height(300.dp),
                                    cameraPositionState = cameraPositionState,
                                    uiSettings = MapUiSettings(
                                        isTiltGesturesEnabled = false,
                                        isRotateGesturesEnabled = false,
                                    )
                                ) {
                                    Marker(
                                        state = MarkerState(position = cameraPositionState.position.target),
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                NPLButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "주소 선택",
                                    onClickEnabled = {
                                        parkingLotData.value = parkingLotData.value.copy(
                                            latlng = GeoPoint(
                                                cameraPositionState.position.target.latitude,
                                                cameraPositionState.position.target.longitude
                                            ),
                                            address = temporalAddressName.value
                                        )
                                        temporalAddressName.value = ""
                                        isAddressSelectorExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        title = "주차장 가격",
                        hint = "가격을 설정하도록 10분당 가격을 입력해주세요",
                        text = parkingLotData.value.pricePer10min?.toString() ?: "",
                        onValueChange = {
                            parkingLotData.value =
                                parkingLotData.value.copy(pricePer10min = it.toIntOrNull() ?: 0)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )

                    val imageUriList = remember { mutableStateListOf<Uri>() }
                    val galleryLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.GetContent()
                    ) { uri -> uri?.let { imageUriList.add(it) } }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        imageUriList.forEach { uri ->
                            OutlinedBox {
                                AsyncImage(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .size(100.dp),
                                    model = uri,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                )
                            }
                        }
                        OutlinedBox(
                            onClick = { galleryLauncher.launch("image/*") },
                        ) {
                            Column(
                                modifier = Modifier.size(100.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_add_circle_24),
                                    contentDescription = null,
                                    tint = Theme.colors.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "사진 추가하기",
                                    style = Theme.typo.body,
                                    color = Theme.colors.onSurface40,
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    NPLButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "주차장 등록",
                        isEnabled =
                        parkingLotData.value.title?.isNotEmpty() == true &&
                                parkingLotData.value.address?.isNotEmpty() == true &&
                                (parkingLotData.value.pricePer10min ?: 0) > 0 &&
                                parkingLotData.value.latlng != null,
                        onClickEnabled = { onClickRegister() }
                    )
                }
            }
        }
    }
}

@Composable
private fun TextFieldLabel(text: String) {
    Text(
        text = text,
        style = Theme.typo.body,
        color = Theme.colors.onBackground0,
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}