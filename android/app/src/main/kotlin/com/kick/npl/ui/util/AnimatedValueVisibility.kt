package com.kick.npl.ui.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.node.Ref

@Composable
inline fun <T> AnimatedValueVisibility(
    value: T?,
    enter: EnterTransition = fadeIn(),
    exit: ExitTransition = fadeOut(),
    crossinline content: @Composable (T) -> Unit
) {
    val ref = remember { Ref<T>() }

    ref.value = value ?: ref.value

    AnimatedVisibility(
        visible = value != null,
        enter = enter,
        exit = exit,
        content = {
            ref.value?.let { value ->
                content(value)
            }
        }
    )
}