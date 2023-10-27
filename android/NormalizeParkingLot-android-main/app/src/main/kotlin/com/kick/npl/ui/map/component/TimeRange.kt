package com.kick.npl.ui.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kick.npl.R
import com.kick.npl.ui.common.ButtonStyle
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.common.NPLButtonSize
import com.kick.npl.ui.theme.NPLTheme
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.toUserFriendlyString
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun TimeRangeBar(
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    onClickChangeButton: () -> Unit = {},
) = Column(
    modifier = Modifier.fillMaxWidth()
) {
    val diff by remember(startTime, endTime) {
        mutableStateOf(Duration.between(startTime, endTime))
    }
    Row(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painterResource(id = R.drawable.ic_access_time_24),
            contentDescription = null,
            tint = Theme.colors.onSurface0,
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = startTime.toUserFriendlyString() + " ~ " + endTime.toUserFriendlyString(),
                fontSize = 13.sp,
                color = Theme.colors.onSurface0,
            )
            Text(
                text = diff.toUserFriendlyString(),
                fontSize = 13.sp,
                color = Theme.colors.onSurface40,
            )
        }
        NPLButton(
            text = "시간변경",
            onClickEnabled = onClickChangeButton,
            buttonStyle = ButtonStyle.Filled,
            size = NPLButtonSize.Small,
            textStyle = Theme.typo.subhead.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
fun TimeRangeBarPreview() {
    NPLTheme {
        TimeRangeBar(
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now().plusHours(1).plusMinutes(12),
        )
    }
}