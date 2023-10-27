package com.kick.npl.ui.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable

@Composable
fun <T> AnimatedPlates(
    value: T?,
    nullableContent: @Composable () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(
        targetState = value,
        label = ""
    ) {
        if (it != null) {
            content(it)
        } else {
            nullableContent()
        }
    }
}