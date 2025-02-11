package com.webxela.app.spinwheel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush

@Stable
data class SpinWheelItem(
    val identifier: String,
    val brush: Brush,
    val title: String,
)


internal fun List<SpinWheelItem>.getDegreesPerItem(): Float =  360f / this.size.toFloat()