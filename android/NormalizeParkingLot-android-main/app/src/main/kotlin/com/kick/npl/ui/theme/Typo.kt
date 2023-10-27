package com.kick.npl.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
data class CustomTypography(
    val titleLarge2B: TextStyle,
    val titleLargeB: TextStyle,
    val titleLarge: TextStyle,
    val title1B: TextStyle,
    val title3B: TextStyle,
    val title3: TextStyle,
    val bodyB: TextStyle,
    val body: TextStyle,
    val subheadB: TextStyle,
    val subhead: TextStyle,
    val footnoteB: TextStyle,
    val footnote: TextStyle,
    val caption1B: TextStyle,
    val caption1: TextStyle,
    val caption2B: TextStyle,
    val caption2: TextStyle,

    val title1: TextStyle,
    val headline: TextStyle
)

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        titleLarge2B = TextStyle.Default,
        titleLargeB = TextStyle.Default,
        titleLarge = TextStyle.Default,
        title1B = TextStyle.Default,
        title3B = TextStyle.Default,
        title3 = TextStyle.Default,
        bodyB = TextStyle.Default,
        body = TextStyle.Default,
        subheadB = TextStyle.Default,
        subhead = TextStyle.Default,
        footnoteB = TextStyle.Default,
        footnote = TextStyle.Default,
        caption1B = TextStyle.Default,
        caption1 = TextStyle.Default,
        caption2B = TextStyle.Default,
        caption2 = TextStyle.Default,

        title1 = TextStyle.Default,
        headline = TextStyle.Default,
    )
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }