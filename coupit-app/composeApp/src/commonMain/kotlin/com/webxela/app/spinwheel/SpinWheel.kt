package com.webxela.app.spinwheel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.min
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SpinWheel(
    spinWheelState: SpinWheelState,
    ringColor: Color = Color(0xFFFFD700),
    indicatorColor: Color = Color(0xFFFF4444),
    centerComponent: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
    ) {
        val wheelSize = min(maxWidth, maxHeight)

        val triangleHeight = wheelSize * 0.10f
        val triangleWidth = wheelSize * 0.10f
        val centerComponentSize = wheelSize * 0.25f

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
                color = ringColor,
                style = Stroke(width = strokeWidth),
                radius = size.minDimension / 2
            )
            drawCircle(
                color = ringColor.copy(alpha = 0.3f),
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
                    color = ringColor,
                    radius = dotRadius,
                    center = Offset(x, y)
                )
            }
        }

        // Center Component
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(centerComponentSize)
                .align(Alignment.Center)
        ) {
            centerComponent()
        }

        // Triangle indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(triangleHeight)
                .align(Alignment.TopCenter)
        ) {
            Canvas(
                modifier = Modifier
                    .size(width = triangleWidth, height = triangleHeight)
                    .align(Alignment.Center)
            ) {
                val path = Path().apply {
                    moveTo(size.width / 2, size.height)
                    lineTo(0f, 0f)
                    lineTo(size.width, 0f)
                    close()
                }

                drawPath(
                    path = path,
                    color = indicatorColor
                )

                drawPath(
                    path = path,
                    color = Color.White.copy(alpha = 0.3f),
                    style = Stroke(width = size.width * 0.1f)
                )
            }
        }
    }
}