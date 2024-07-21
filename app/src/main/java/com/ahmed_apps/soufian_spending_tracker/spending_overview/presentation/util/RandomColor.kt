package com.ahmed_apps.soufian_spending_tracker.spending_overview.presentation.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

/**
 * @author Ahmed Guedmioui
 */


fun randomColor(minBrightness: Int = 60): Color {
    val random = Random.Default
    val red = random.nextInt(minBrightness, 256)
    val green = random.nextInt(minBrightness, 256)
    val blue = random.nextInt(minBrightness, 256)
    return Color(red, green, blue).copy(0.3f)
}