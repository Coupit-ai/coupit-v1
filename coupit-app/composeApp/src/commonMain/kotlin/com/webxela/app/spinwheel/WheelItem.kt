package com.webxela.app.spinwheel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush

@Stable
data class SpinWheelItem(
    val offerId: String,
    val title: String,
    val description: String,
    val color: Brush,
    val content: @Composable () -> Unit,
)


internal fun List<SpinWheelItem>.getDegreesPerItem(): Float =  360f / this.size.toFloat()