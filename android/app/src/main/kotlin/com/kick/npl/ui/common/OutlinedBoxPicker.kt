package com.kick.npl.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kick.npl.R
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable

@Composable
fun OutlinedBoxPicker(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    text: String,
    textStyle: TextStyle = Theme.typo.body,
    textColor: Color = Theme.colors.onSurface0,
    visibleArrow: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedBox(modifier, onClick) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedBoxTitleDesc(title, text, textColor, textStyle)
            }
            if (visibleArrow) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    tint = Theme.colors.onSurface0
                )
            }
        }
    }
}

@Composable
fun ExpandableOutlinedBox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    text: String,
    textStyle: TextStyle = Theme.typo.body,
    textColor: Color = Theme.colors.onSurface0,
    expanded: Boolean = false,
    onClick: () -> Unit,
    toggleExpansion: (() -> Unit)? = null,
    content: @Composable (onAnimationEnd: Boolean) -> Unit = {}
) {
    val rotateArrow by animateFloatAsState(targetValue = if (expanded) -180f else 0f)

    OutlinedBox(modifier, onClick) {
        Column {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable { toggleExpansion?.invoke() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        OutlinedBoxTitleDesc(title, text, textColor, textStyle)
                    }

                    Icon(
                        modifier = Modifier.rotate(rotateArrow),
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = null,
                        tint = Theme.colors.onSurface0
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                content(rotateArrow == -180f || rotateArrow == 0f)
            }
        }
    }
}

@Composable
fun OutlinedBoxText(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    text: String,
    textStyle: TextStyle = Theme.typo.body,
    textColor: Color = Theme.colors.onSurface0
) {
    OutlinedBox(modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedBoxTitleDesc(title, text, textColor, textStyle)
        }
    }
}

@Composable
fun OutlinedTextField(
    title: String,
    hint: String? = null,
    text: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    onFocus: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedBox {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedBoxTitle(title)
            Spacer(modifier = Modifier.height(6.dp))
            Box {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (focusState.hasFocus) onFocus()
                        }
                        .run { focusRequester?.let { focusRequester(it) } ?: this },
                    value = text,
                    onValueChange = onValueChange,
                    textStyle = Theme.typo.body.copy(color = Theme.colors.onSurface0),
                    cursorBrush = SolidColor(Theme.colors.primary),
                    singleLine = true,
                    keyboardOptions = keyboardOptions
                )
                if (hint != null && text.isEmpty()) {
                    Text(
                        text = hint,
                        style = Theme.typo.body,
                        color = Theme.colors.onSurface70
                    )
                }
            }
        }
    }
}

@Composable
fun OutlinedBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Theme.colors.secondaryLine,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Theme.colors.surface99, RoundedCornerShape(12.dp))
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        content()
    }
}

@Composable
fun DashedOutlinedBox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp,
    color: Color = Theme.colors.line,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.noRippleClickable(onClick = onClick)
    ) {
        Canvas(
            Modifier
                .fillMaxWidth()
                .height(height)
                .background(Theme.colors.surface99, RoundedCornerShape(12.dp))
        ) {
            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
            )
        }
        content()
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