package com.webxela.app.spinwheel


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.sin

@Composable
internal fun WheelSlice(
    modifier: Modifier = Modifier,
    size: Dp,
    brush: Brush,
    degree: Float,  // in degrees
    content: String,
) {
    val density = LocalDensity.current
    var canvasWidth by remember { mutableStateOf(0) }
    val radiusPx = with(density) { (size / 2).toPx() }

    val theta = degree * PI / 180.0

    val centroidDistancePx = if (theta > 0) {
        (4 * radiusPx.toDouble() * sin(theta / 2)) / (3 * theta)
    } else {
        radiusPx.toDouble() / 2
    }

    // Convert the computed offset back to dp.
    val offsetDp = with(density) { centroidDistancePx.toFloat().toDp() }

    Box(modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.size(size).onSizeChanged { canvasWidth = it.width }) {
            // Draw the wedge (arc) centered around the top (-90°)
            drawArc(
                brush = brush,
                startAngle = -90f - (degree / 2),
                sweepAngle = degree,
                useCenter = true,
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = -offsetDp)
                .rotate(90f) // Adjust this rotation as needed for your design.
        ) {
            Text(
                text = content,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.sizeIn(maxWidth = 200.dp),
            )
        }
    }
}