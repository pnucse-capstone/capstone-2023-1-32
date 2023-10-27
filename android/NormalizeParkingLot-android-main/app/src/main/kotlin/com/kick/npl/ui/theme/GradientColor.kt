package com.kick.npl.ui.theme

import androidx.compose.ui.graphics.Color

data class GradientColor(
    val start: Color,
    val end: Color,
) {
    constructor(vararg colorLong : Long) : this(Color(colorLong.first()), Color(colorLong.last())) {
        require(colorLong.size >= 2) { "GradientColor must have at least 2 colors" }
    }
    companion object {
        val Unspecified = GradientColor(Color.Unspecified, Color.Unspecified)
    }
}