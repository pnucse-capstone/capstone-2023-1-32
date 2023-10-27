package com.kick.npl.ui.common

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kick.npl.R
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.kick.npl.ui.theme.NPLTheme
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable
import com.naver.maps.geometry.LatLng

@Preview
@Composable
private fun Preview() {
    NPLTheme {
        ParkingLotCard(
            parkingLotData = ParkingLotData(
                name = "테스트 주차장",
                address = "서울특별시 강남구 테헤란로 427",
                addressDetail = "테스트 주차장",
                pricePer10min = 1000,
                latLng = LatLng(37.498095, 127.031693),
                id = "4389",
                imageUri = "sanctus",
                favorite = false,
                parkingLotType = ParkingLotType.TYPE_A,
            )
        )
    }
}

@Composable
fun ParkingLotCard(
    parkingLotData: ParkingLotData?,
    modifier: Modifier = Modifier,
    haveBorder: Boolean = false,
    onClickCard: () -> Unit = {},
    onClickFavorite: () -> Unit = {},
) = Card(
    modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 100.dp),
    shape = CardDefaults.elevatedShape,
    colors = CardDefaults.elevatedCardColors(
        containerColor = Theme.colors.surface,
        contentColor = Theme.colors.onSurface0,
    ),
    border = if (haveBorder) CardDefaults.outlinedCardBorder() else null,
    elevation = if (haveBorder) CardDefaults.cardElevation() else CardDefaults.elevatedCardElevation()
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        parkingLotData?.let { parkingLotData ->
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .size(68.dp),
                model = parkingLotData.imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = parkingLotData.name,
            )
            ParkingLotCardContent(
                parkingLotData = parkingLotData,
                onClickCard = onClickCard,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@Composable
fun ParkingLotCardContent(
    parkingLotData: ParkingLotData,
    onClickCard: () -> Unit = {},
    onClickFavorite: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onClickCard() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = parkingLotData.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = parkingLotData.address,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Icon(
                painterResource(
                    if (parkingLotData.favorite) R.drawable.ic_star_filled
                    else R.drawable.ic_star_border
                ),
                contentDescription = null,
                tint = if (parkingLotData.favorite) Color(0xFFFFCA00) else Theme.colors.line,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onClickFavorite)
            )
        }
        Text(
            text = buildAnnotatedString {
                append("10분당 ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    )
                ) {
                    append(DecimalFormat("#,###").format(parkingLotData.pricePer10min))
                }
                withStyle(
                    SpanStyle(
                        fontSize = 20.sp,
                    )

                ) { append("원") }
            },
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.End)
        )
    }
}
