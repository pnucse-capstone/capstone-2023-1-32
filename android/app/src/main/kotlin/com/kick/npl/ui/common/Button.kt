package com.kick.npl.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.conditional
import com.kick.npl.ui.util.noRippleClickable

@Composable
fun NPLButton(
    text: String,
    modifier: Modifier = Modifier,
    size: NPLButtonSize = NPLButtonDefaults.buttonSize,
    buttonStyle: ButtonStyle = NPLButtonDefaults.buttonStyle,
    textStyle: TextStyle? = null,
    buttonShape: Shape? = null,
    filledColor: Color? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClickDisabled: () -> Unit = {},
    onClickEnabled: () -> Unit = {},
) {
    val containerColor = filledColor ?: when (buttonStyle) {
        ButtonStyle.Filled -> Theme.colors.primaryContainer
        else -> Color.Transparent
    }

    val contentColor = when (buttonStyle) {
        ButtonStyle.Ghost -> Theme.colors.primary
        ButtonStyle.Filled -> Theme.colors.onPrimaryContainer
        ButtonStyle.Outline -> when (size) {
            NPLButtonSize.Small -> Theme.colors.onSurface40
            NPLButtonSize.Large -> Theme.colors.onSurface0
        }
    }

    val border = when (buttonStyle) {
        ButtonStyle.Outline -> BorderStroke(1.dp, Theme.colors.line)
        else -> null
    }

    val typo = when (buttonStyle) {
        ButtonStyle.Filled -> when (size) {
            NPLButtonSize.Small -> Theme.typo.subhead
            NPLButtonSize.Large -> Theme.typo.bodyB
        }

        ButtonStyle.Outline -> when (size) {
            NPLButtonSize.Small -> Theme.typo.subhead
            NPLButtonSize.Large -> Theme.typo.body
        }

        ButtonStyle.Ghost -> Theme.typo.body
    }

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .conditional(!isLoading) {
                val clickEvent = remember(isEnabled) {
                    if (isEnabled) onClickEnabled else onClickDisabled
                }
                noRippleClickable(clickEvent)
            }

    ) {
        AnimatedVisibility(
            visible = isLoading,
            modifier = Modifier.align(Alignment.Center)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = Theme.colors.onPrimaryContainer
            )
        }

        Text(
            text = text,
            style = textStyle ?: typo,
            color = if (isEnabled) contentColor else Theme.colors.surface,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (isLoading) 0f else 1f)
                .sizeIn(minHeight = size.minimumHeight)
                .conditional(border != null) {
                    border(border!!, buttonShape ?: size.buttonShape)
                }
                .background(
                    color = if (isEnabled) containerColor else Theme.colors.onSurface70,
                    shape = buttonShape ?: size.buttonShape
                )
                .padding(size.contentPadding)
        )
    }
}

object NPLButtonDefaults {
    val buttonSize = NPLButtonSize.Large
    val buttonStyle = ButtonStyle.Filled
}

enum class NPLButtonSize(
    val contentPadding: PaddingValues,
    val buttonShape: Shape,
    val minimumHeight: Dp,
) {
    Small(PaddingValues(12.dp, 8.dp), RoundedCornerShape(12.dp), 36.dp),
    Large(PaddingValues(24.dp, 16.dp), RoundedCornerShape(20.dp), 54.dp)
}

enum class ButtonStyle {
    Outline, Filled, Ghost
}