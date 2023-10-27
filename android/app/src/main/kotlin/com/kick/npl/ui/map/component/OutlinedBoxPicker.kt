package com.kick.npl.ui.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kick.npl.R
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable

@Composable
fun OutlinedBoxPicker(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = Theme.typo.body,
    textColor: Color = Theme.colors.onSurface0,
    visibleArrow: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedBox(modifier.fillMaxWidth(), onClick) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedBoxTitleDesc(title, text, textColor, textStyle)
            }
            if (visibleArrow) {
                Icon(
                    painter = painterResource(id = R.drawable.round_keyboard_arrow_down_24),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    tint = Theme.colors.onSurface0
                )
            }
        }
    }
}

@Composable
fun OutlinedBoxTitle(title: String) {
    Text(
        text = title,
        style = Theme.typo.footnote,
        color = Theme.colors.onSurface40
    )
}

@Composable
private fun OutlinedBoxTitleDesc(
    title: String,
    description: String,
    descColor: Color,
    style: TextStyle
) {
    OutlinedBoxTitle(title)
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = description,
        style = style,
        color = descColor
    )
}

@Composable
fun OutlinedBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    outlineColor: Color = Theme.colors.secondaryLine,
    cornerRadiusDp: Dp = 12.dp,
    innerPaddingHorizontal: Dp = 16.dp,
    innerPaddingVertical: Dp = 18.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .border(
                width = 1.dp,
                color = outlineColor,
                shape = RoundedCornerShape(cornerRadiusDp)
            )
            .background(Theme.colors.surface99, RoundedCornerShape(cornerRadiusDp))
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = innerPaddingHorizontal, vertical = innerPaddingVertical)
    ) {
        content()
    }
}