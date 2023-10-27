package com.kick.npl.ui.parkinglot

import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kick.npl.R
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.common.ButtonStyle
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.map.model.ParkingDateTime
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable
import com.kick.npl.ui.util.toUserFriendlyString
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage
import java.text.DecimalFormat
import java.time.Duration

@Composable
fun ParkingLotDetailScreen(
    parkingLotData: ParkingLotData,
    parkingDateTime: ParkingDateTime?,
    onClickClose: () -> Unit,
    viewModel: ParkingLotDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel.paymentResult) {
        viewModel.result.collect {
            when (it) {
                is ParkingLotDetailViewModel.PaymentResult.Success -> {
                    Toast.makeText(
                        context,
                        "주차장 대여 신청이 완료되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClickClose()
                }

                is ParkingLotDetailViewModel.PaymentResult.Failure -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    var isAllAgreed by remember { mutableStateOf(false) }
    val isPaymentEnable = parkingDateTime != null
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background),
    ) {
        TopBar(onClickClose = onClickClose)

        AnimatedContent(targetState = viewModel.isLoading, label = "") {
            when (it) {
                true -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Theme.colors.primary
                        )
                    }
                }

                false -> {
                    Box {
                        ParkingLotDetailContent(
                            parkingLotData,
                            parkingDateTime,
                            isAllAgreed,
                            onClickAgree = { isAllAgreed = it },
                            isAgreementEnable = isPaymentEnable,
                        )
                        FloatingButton(
                            price = if (isPaymentEnable) {
                                (parkingLotData.pricePer10min * Duration.between(
                                    parkingDateTime!!.startTime,
                                    parkingDateTime.endTime
                                ).toMinutes()).div(10)
                            } else {
                                null
                            },
                            isEnabled = isAllAgreed,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            onClickButton = {
                                if (isPaymentEnable) {
                                    viewModel.updateField(parkingLotData.id, false)
                                } else {
                                    onClickClose()
                                }
                            },
                        )
                    }
                }
            }

        }
    }
}


@Composable
private fun TopBar(onClickClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.colors.surface)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_navigate_before_24),
            contentDescription = null,
            tint = Theme.colors.onSurface0,
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
                .noRippleClickable(onClick = onClickClose)
                .align(Alignment.CenterStart)
        )
        Text(
            text = "주차장 예약하기",
            style = Theme.typo.subheadB,
            color = Theme.colors.onSurface0,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun FloatingButton(
    price: Long?,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .shadow(8.dp)
            .background(color = Theme.colors.surface)
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        NPLButton(
            text = if (price != null) "총 ${DecimalFormat("##,###").format(price)} 원 결제하기" else "확인",
            buttonStyle = ButtonStyle.Filled,
            isEnabled = if (price != null) isEnabled else true,
            modifier = Modifier
                .fillMaxWidth(),
            onClickEnabled = onClickButton
        )
    }
}

@Composable
private fun ParkingLotDetailContent(
    parkingLotData: ParkingLotData,
    parkingDateTime: ParkingDateTime?,
    isAgreed: Boolean,
    onClickAgree: (Boolean) -> Unit = {},
    isAgreementEnable: Boolean,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(Modifier.height(4.dp))
        BasicInfoSection(parkingLotData)
        LocationInfoSection(parkingLotData)
        parkingDateTime?.let { PeriodSection(it) }
        if (parkingDateTime != null) {
            PaymentSection(
                parkingLotData.pricePer10min,
                Duration.between(parkingDateTime.startTime, parkingDateTime.endTime)
            )
        } else {
            PriceInfoSection(parkingLotData.pricePer10min)
        }

        PointSection(isAgreementEnable, isAgreed, onClickAgree)
        AgreementSection()
    }
}

@Composable
private fun BasicInfoSection(parkingLotData: ParkingLotData) {
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .size(80.dp),
                model = parkingLotData.imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = parkingLotData.name,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = parkingLotData.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = parkingLotData.address,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "주차요금",
                style = Theme.typo.subheadB,
                color = Theme.colors.onSurface40,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .padding(start = 4.dp)
            )
            Text(
                text = buildAnnotatedString {
                    append("10분당 ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    ) {
                        append(
                            android.icu.text.DecimalFormat("#,###")
                                .format(parkingLotData.pricePer10min)
                        )
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                        )
                    ) { append("원") }
                }, fontSize = 16.sp, modifier = Modifier
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun LocationInfoSection(parkingLotData: ParkingLotData) {
    val cameraPositionState by remember {
        mutableStateOf(CameraPositionState())
    }.apply {
        value.position = CameraPosition(parkingLotData.latLng, 16.0)
    }
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = "위치 정보",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))

        NaverMap(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(200.dp),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isBuildingLayerGroupEnabled = false,
                isNightModeEnabled = isSystemInDarkTheme(),
            ),
            uiSettings = MapUiSettings(
                isTiltGesturesEnabled = false,
                isZoomGesturesEnabled = false,
                isCompassEnabled = false,
                isZoomControlEnabled = true,
                isRotateGesturesEnabled = false,
                isScaleBarEnabled = false,
                isLocationButtonEnabled = false,
                isLogoClickEnabled = false,
                logoGravity = Gravity.TOP or Gravity.START
            ),
            locationSource = rememberFusedLocationSource(),
        ) {
            Marker(
                icon = OverlayImage.fromResource(R.drawable.ic_marker_selected),
                iconTintColor = Theme.colors.primary,
                state = MarkerState(parkingLotData.latLng),
                captionMinZoom = 11.0,
                subCaptionMinZoom = 11.0,
                captionText = parkingLotData.name,
            )
        }
    }
}

@Composable
private fun PeriodSection(parkingDateTime: ParkingDateTime) {
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = "주차 기간",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_access_time_24),
                contentDescription = null,
                tint = Theme.colors.onSurface0,
            )
            Column {
                val start = parkingDateTime.startTime
                val end = parkingDateTime.endTime
                Text(
                    text = start.toUserFriendlyString() + " ~ " + end.toUserFriendlyString(),
                    fontSize = 13.sp,
                    color = Theme.colors.onSurface0,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = Duration.between(start, end).toUserFriendlyString(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Theme.colors.onSurface40,
                )
            }
        }

    }
}

@Composable
private fun PaymentSection(
    price: Int,
    duration: Duration,
) {
    val priceString = DecimalFormat("###,###").format((price * duration.toMinutes()).div(10)) + "원"
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = "결제 정보",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "주차 요금",
                style = Theme.typo.subheadB,
                color = Theme.colors.onSurface40,
            )
            Text(
                text = priceString,
                style = Theme.typo.subheadB,
                color = Theme.colors.onSurface0,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "할인 적립",
                style = Theme.typo.subheadB,
                color = Theme.colors.onSurface40,
            )
            Text(
                text = "해당없음",
                style = Theme.typo.subhead,
                color = Theme.colors.onSurface40,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp),
            color = Theme.colors.line,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "최종금액",
                style = Theme.typo.title3B,
                color = Theme.colors.onSurface0,
            )
            Text(
                text = priceString,
                style = Theme.typo.title3B,
                color = Theme.colors.onSurface0,
            )
        }
    }
}

@Composable
fun PriceInfoSection(pricePer10min: Int) {
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = "가격 정보",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "주차 요금",
                style = Theme.typo.subheadB,
                color = Theme.colors.onSurface40,
            )
            Text(
                text = buildAnnotatedString {
                    append("10분당 ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    ) {
                        append(android.icu.text.DecimalFormat("#,###").format(pricePer10min))
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                        )
                    ) { append("원") }
                }, fontSize = 16.sp, modifier = Modifier
            )
        }
    }
}

@Composable
fun PointSection(
    isAgreementEnable: Boolean,
    isAgreed: Boolean,
    onClickAgree: (Boolean) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(color = Theme.colors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = "주차장 안내",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "주차 규칙 준수\n\n주차장의 규칙을 엄격히 준수해야 합니다. 주차 시간, 주차 구역, 속도 제한 등을 포함한 주차장 규정을 준수해야합니다.",
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "주차장 상태 확인\n\n주차장 상태를 확인하고 문제가 있는 경우 서비스 제공자에게 보고해야 합니다. 이는 다른 사용자들을 위한 안전과 주차장의 관리를 돕는 데 중요합니다.",
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "예약 시간 준수\n\n주차장을 예약한 시간을 엄수해야 합니다. 다른 사용자들도 주차 공간을 필요로 할 수 있으므로 시간을 넘어가는 경우 추가 요금이나 벌금이 부과될 수 있습니다.",
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "주차장 청결 유지\n\n주차 후에는 쓰레기를 버리지 않고 주차장을 깨끗하게 유지해야 합니다. 이는 다른 사용자들과 환경을 존중하는 일환입니다.",
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "고객 서비스 지원\n\n문의나 불만 사항에 대한 고객 서비스를 제공합니다. 문제나 질문에 있으면 고객센터로 연락 부탁드립니다.",
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (isAgreementEnable) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isAgreed, onCheckedChange = onClickAgree)
                ClickableText(
                    text = AnnotatedString("위 내용에 모두 동의합니다"),
                    onClick = { onClickAgree(!isAgreed) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AgreementSection() {

}

