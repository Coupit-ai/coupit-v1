package com.webxela.app.spinwheel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun WheelPointer(
    modifier: Modifier = Modifier,
    fillColor: Color = Color.Black,
    strokeColor: Color = Color.White,
    strokeWidth: Float = 3.0f,
    content: @Composable () -> Unit
) {

    var pointerSize by remember { mutableStateOf(IntSize(0, 0)) }

    val imageVector = ImageVector.Builder(
        name = "WheelPointer",
        defaultWidth = 100.dp,
        defaultHeight = 100.dp,
        viewportWidth = 100f,
        viewportHeight = 100f
    ).apply {
        path(
            fill = SolidColor(fillColor),
            fillAlpha = 1.0f,
            stroke = SolidColor(strokeColor),
            strokeAlpha = 1.0f,
            strokeLineWidth = strokeWidth,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(40.56612f, 24.292673f)
            lineTo(50f, 7.547533f)
            lineTo(59.43388f, 24.292673f)
            arcTo(
                28.363846f,
                28.363846f, 0f, isMoreThanHalf = true, isPositiveArc = true,
                40.56612f, 24.292673f
            )
            close()
        }
    }.build()

    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = rememberVectorPainter(imageVector),
            contentDescription = "Pointer",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.onSizeChanged {
                pointerSize = it / 2
            }
        )
        Box(
            modifier = Modifier
                .sizeIn(
                    maxWidth = pointerSize.width.dp,
                    maxHeight = pointerSize.width.dp
                )
                .fillMaxSize(.5f),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }


}