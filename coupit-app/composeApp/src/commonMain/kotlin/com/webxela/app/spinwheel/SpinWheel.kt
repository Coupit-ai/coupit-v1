package com.webxela.app.spinwheel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SpinWheel(
    spinWheelState: SpinWheelState,
    outerRingColor: Color = Color(0xFFFFD700),
    pointerColor: Color = Color(0xFF008000),
    pointerStrokeColor: Color = Color(0xFFFFA500),
    pointerStrokeWidth: Float = 3.0f,
    centerComponent: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
    ) {

        // Main Wheel
        Wheel(
            wheelItems = spinWheelState.wheelItems,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = spinWheelState.rotation.value }
        )

        // Decorative Ring
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = size.minDimension * 0.03f // 3% of wheel size

            drawCircle(
                color = outerRingColor,
                style = Stroke(width = strokeWidth),
                radius = size.minDimension / 2
            )
            drawCircle(
                color = outerRingColor.copy(alpha = 0.3f),
                style = Stroke(width = strokeWidth / 2),
                radius = (size.minDimension / 2) - strokeWidth * 1.2f
            )

            // Decorative dots
            val centerX = size.width / 2
            val centerY = size.height / 2
            val dotRadius = size.minDimension * 0.008f // 0.8% of wheel size
            val dotsRadius = size.minDimension / 2 - strokeWidth / 2

            for (i in 0 until 36) {
                val angle = (i * 10 * PI) / 180
                val x = centerX + dotsRadius * cos(angle).toFloat()
                val y = centerY + dotsRadius * sin(angle).toFloat()
                drawCircle(
                    color = outerRingColor,
                    radius = dotRadius,
                    center = Offset(x, y)
                )
            }
        }

        // Center Component
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            WheelPointer { centerComponent() }
        }
    }
}