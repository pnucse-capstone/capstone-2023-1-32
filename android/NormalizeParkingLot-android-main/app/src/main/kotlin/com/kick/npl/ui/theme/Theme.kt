package com.kick.npl.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val DarkColorScheme = CustomColors(
    primary = Color(0xFFFFCA00),
    primaryContainer = Color(0xFFFFCA00),
    onPrimaryContainer = Color.Black,
    background = Color(0xFF01081F),
    surface = Color(0xFF01081F),
    surface99 = Color(0xFF10192E),
    surface90 = Color(0xFF202A3D),
    line = Color(0x29FFFFFF),
    secondaryLine = Color(0x14FFFFFF),
    onBackground0 = Color(0xFFF0F1F5),
    onBackground40 = Color(0xFF7A7C85),
    onBackground60 = Color(0xFF585D6B),
    onBackground70 = Color(0xFF464B5D),
    onSurface0 = Color(0xFFEDEEF2),
    onSurface40 = Color(0xFF909198),
    onSurface60 = Color(0xFF626573),
    onSurface70 = Color(0xFF464B5D),
    onSurface90 = Color(0xFFF3F4F6),
    secondarySurface = Color.Black,
    secondarySurface40 = Color(0xFF64656B),
    secondarySurface50 = Color(0x8C00030A),
    secondarySurface90 = Color(0x1400030A),
    onSecondarySurface0 = Color.White,
    onBackgroundGradation = listOf(Color(0xF501081F), Color(0x0001081F)),
    onSurfaceGradation = listOf(Color(0xF501081F), Color(0x0001081F)),
    onSurface99Gradation = listOf(Color(0xF510192E), Color(0x0010192E)),
    onSurface90Gradation = listOf(Color(0xF501081F), Color(0x0001081F)),
    error = Color(0xFFFF575C),
    // Gradation
    onBackgroundGradientColor = GradientColor(0xF501081F, 0x0001081F),
    onSurfaceGradientColor = GradientColor(0xF501081F, 0x0001081F),
    onSurface99GradientColor = GradientColor(0xF510192E, 0x0010192E),
    onSurface90GradientColor = GradientColor(0xF501081F, 0x0001081F),
    // Graph
    graphHigh = Color(0xFFF58514),
    graphHigh40 = Color(0xFFE84F02),
    graphHigh80 = Color(0xFF472314),
    graphHigh90 = Color(0xFF472F1A),
    graphCaution = Color(0xFFFF8C00),
    graphCaution90 = Color(0xFF4D341D),
    graphSafety = Color(0xFF4076B5),
    graphSafety10 = Color(0xFFD2D3D6),
    graphSafety40 = Color(0xFF5094E2),
    graphSafety90 = Color(0xFF0C233D),
    graphSafety99 = Color(0xFF001833),
    graphLow = Color(0xFFF01952),
    graphLow40 = Color(0xFFFF578F),
)

private val LightColorScheme = CustomColors(
    primary = Color(0xFF175CE5),
    primaryContainer = Color(0xFF175CE5),
    onPrimaryContainer = Color.White,
    background = Color(0xFFF3F4F6),
    surface = Color.White,
    surface90 = Color(0xFFF3F4F6),
    surface99 = Color.White,
    line = Color(0x2914181F),
    secondaryLine = Color(0x1414181F),
    onBackground0 = Color(0xFF11141A),
    onBackground40 = Color(0xFF6C6E73),
    onBackground60 = Color(0xFF8A8D93),
    onBackground70 = Color(0xFFABADB0),
    onSurface0 = Color(0xFF11141A),
    onSurface40 = Color(0xFF6C6E73),
    onSurface60 = Color(0xFF92959B),
    onSurface70 = Color(0xFFB5B7B9),
    onSurface90 = Color(0xFFF3F4F6),
    secondarySurface = Color.Black,
    secondarySurface40 = Color(0xFF64656B),
    secondarySurface50 = Color(0x8C00030A),
    secondarySurface90 = Color(0x1400030A),
    onSecondarySurface0 = Color.White,
    onBackgroundGradation = listOf(Color(0xF5FFFFFF), Color(0x00FFFFFF)),
    onSurfaceGradation = listOf(Color(0xF5FFFFFF), Color(0x00FFFFFF)),
    onSurface99Gradation = listOf(Color(0xF5FFFFFF), Color(0x00FFFFFF)),
    onSurface90Gradation = listOf(Color(0xF5FFFFFF), Color(0x00FFFFFF)),
    error = Color(0xFFE0001E),
    // Gradation
    onBackgroundGradientColor = GradientColor(0xF5FFFFFF, 0x00FFFFFF),
    onSurfaceGradientColor = GradientColor(0xF5FFFFFF, 0x00FFFFFF),
    onSurface99GradientColor = GradientColor(0xF5FFFFFF, 0x00FFFFFF),
    onSurface90GradientColor = GradientColor(0xF5FFFFFF, 0x00FFFFFF),
    // Graph
    graphHigh = Color(0xFFF55905),
    graphHigh40 = Color(0xFFD12A00),
    graphHigh80 = Color(0xFFFFC7BA),
    graphHigh90 = Color(0xFFFFDAC7),
    graphCaution = Color(0xFFFF8C00),
    graphCaution90 = Color(0xFFFFE8CC),
    graphSafety = Color(0xFF00C608),
    graphSafety10 = Color(0xFF171F1E),
    graphSafety40 = Color(0xFF008A04),
    graphSafety90 = Color(0xFFD6F6D7),
    graphSafety99 = Color(0xFFF2FCF3),
    graphLow = Color(0xFFEB004E),
    graphLow40 = Color(0xFFAD002E)
)


@Composable
fun NPLTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    preventSystemUiModification: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode && !preventSystemUiModification) {
        SideEffect {
            val window = (view.context as Activity).window
            // TODO 투명처리
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    val customTypography = CustomTypography(
        titleLarge2B = TextStyle(fontWeight = FontWeight.Bold, fontSize = dpToSp(dp = 40.dp)),
        titleLargeB = TextStyle(fontWeight = FontWeight.Bold, fontSize = dpToSp(dp = 34.dp)),
        titleLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = dpToSp(dp = 34.dp)),
        title1B = TextStyle(fontWeight = FontWeight.Bold, fontSize = dpToSp(dp = 28.dp)),
        title3B = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
        title3 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 20.sp),
        bodyB = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
        body = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
        subheadB = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
        subhead = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp),
        footnoteB = TextStyle(fontWeight = FontWeight.Bold, fontSize = 13.sp),
        footnote = TextStyle(fontWeight = FontWeight.Normal, fontSize = 13.sp),
        caption1B = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
        caption1 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp),
        caption2B = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp),
        caption2 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 11.sp),

        title1 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 28.sp),
//        title2 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 22.sp),
        headline = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
//        callout = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
        )

    CompositionLocalProvider(
        LocalCustomColors provides colorScheme,
        LocalCustomTypography provides customTypography,
        content = content
    )
}

object Theme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current

    val typo: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
}