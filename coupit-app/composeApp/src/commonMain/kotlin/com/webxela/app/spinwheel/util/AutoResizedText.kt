package com.webxela.app.spinwheel.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp

@Composable
internal fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    modifier: Modifier = Modifier,
    color: Color = style.color,
    minFontSize: Float = 8f // Prevent text from becoming unreadable
) {
    var resizedTextStyle by remember { mutableStateOf(style) }
    var shouldDraw by remember { mutableStateOf(false) }
    val defaultFontSize = if (style.fontSize.isUnspecified) {
        MaterialTheme.typography.titleLarge.fontSize
    } else {
        style.fontSize
    }
    var currentFontSize by remember { mutableStateOf(defaultFontSize) }

    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        maxLines = 1,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (currentFontSize.value > minFontSize) {
                    currentFontSize = (currentFontSize.value * 0.95f).coerceAtLeast(minFontSize).sp
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = currentFontSize
                    )
                } else {
                    shouldDraw = true // Draw anyway if we hit the minimum size
                }
            } else {
                shouldDraw = true
            }
        }
    )
}