package com.kick.npl.ui.setting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.common.ButtonStyle
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.theme.Theme

@Composable
fun SettingScreen(
    parkingLotData: ParkingLotData? = null,
    getParkingLotData: (String) -> Unit = {},
    updateField: (Boolean) -> Unit = {},
    mockTestData: () -> Unit = {},
    deleteTestData: () -> Unit = {},
    logout: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Theme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        var inputId by remember { mutableStateOf("") }

        TextField(
            label = {
                Text(text = "테스트 데이터 ID")
            },
            value = inputId,
            onValueChange = { inputId = it }
        )

        parkingLotData?.let {
            Column(
                modifier = Modifier
                    .border(1.dp, Theme.colors.onBackground0)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = it.id)
                Text(text = it.name)
                Text(text = it.address)
                Text(text = it.favorite.toString())
                Text(text = it.imageUri)
                Text(text = it.pricePer10min.toString())

                Divider(modifier = Modifier.border(1.dp, Theme.colors.onBackground0))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    NPLButton(
                        buttonStyle = ButtonStyle.Outline,
                        text = "차단기 열기",
                        onClickEnabled = { updateField(false) }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    NPLButton(
                        buttonStyle = ButtonStyle.Outline,
                        text = "차단기 닫기",
                        onClickEnabled = { updateField(true) }
                    )
                }
            }
        }

        NPLButton(
            buttonStyle = ButtonStyle.Filled,
            text = "테스트 데이터 검색",
            onClickEnabled = {
                getParkingLotData(inputId)
            },
        )

        NPLButton(
            buttonStyle = ButtonStyle.Filled,
            text = "테스트 데이터 수정",
            onClickEnabled = {},
        )

        NPLButton(
            buttonStyle = ButtonStyle.Filled,
            text = "테스트 데이터 전체 추가",
            onClickEnabled = mockTestData,
        )

        NPLButton(
            buttonStyle = ButtonStyle.Filled,
            text = "테스트 데이터 추가",
            onClickEnabled = {},
        )

        NPLButton(
            buttonStyle = ButtonStyle.Filled,
            text = "테스트 데이터 전체 삭제",
            onClickEnabled = deleteTestData,
        )
    }
}
