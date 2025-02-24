package com.webxela.app.spinwheel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SpinWheel(
    spinWheelState: SpinWheelState,
    modifier: Modifier = Modifier,
    outerRingColor: Color = Color(0xFFFFD700),
    pointerColor: Color = Color(0xFF000000),
    pointerStrokeColor: Color = Color(0xFFEDEADE),
    pointerStrokeWidth: Float = 3.0f,
    centerComponent: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
    ) {
        val wheelConfig = remember(maxWidth, maxHeight) {
            WheelConfiguration(
                strokeWidth = minOf(maxWidth, maxHeight).value * 0.03f,
                dotRadius = minOf(maxWidth, maxHeight).value * 0.008f
            )
        }

        // Main Wheel
        Wheel(
            wheelItems = spinWheelState.wheelItems,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationZ = spinWheelState.rotation.value
                    clip = true
                }
        )

//        // Decorative Ring
//        DecorationRing(
//            outerRingColor = outerRingColor,
//            config = wheelConfig
//        )

        // Center Component with Pointer
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            WheelPointer(
                fillColor = pointerColor,
                strokeColor = pointerStrokeColor,
                strokeWidth = pointerStrokeWidth
            ) {
                centerComponent()
            }
        }
    }
}

private data class WheelConfiguration(
    val strokeWidth: Float,
    val dotRadius: Float
)

@Composable
private fun DecorationRing(
    outerRingColor: Color,
    config: WheelConfiguration,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawOuterRings(outerRingColor, config.strokeWidth)
        drawDecorativeDots(outerRingColor, config)
    }
}

private fun DrawScope.drawOuterRings(color: Color, strokeWidth: Float) {
    val radius = size.minDimension / 2

    // Main outer ring
    drawCircle(
        color = color,
        style = Stroke(width = strokeWidth),
        radius = radius
    )

    // Inner decorative ring
    drawCircle(
        color = color.copy(alpha = 0.3f),
        style = Stroke(width = strokeWidth / 2),
        radius = radius - strokeWidth * 1.2f
    )
}

private fun DrawScope.drawDecorativeDots(color: Color, config: WheelConfiguration) {
    val center = Offset(size.width / 2, size.height / 2)
    val dotsRadius = size.minDimension / 2 - config.strokeWidth / 2

    repeat(36) { i ->
        val angle = (i * 10 * PI) / 180
        val x = center.x + dotsRadius * cos(angle).toFloat()
        val y = center.y + dotsRadius * sin(angle).toFloat()

        drawCircle(
            color = color,
            radius = config.dotRadius,
            center = Offset(x, y)
        )
    }
}