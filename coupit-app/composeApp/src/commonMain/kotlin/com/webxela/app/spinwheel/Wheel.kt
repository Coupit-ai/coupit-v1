package com.webxela.app.spinwheel

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.min

@Composable
internal fun Wheel(
    modifier: Modifier = Modifier,
    wheelItems: List<SpinWheelItem>,
) {
    BoxWithConstraints(modifier = modifier) {

        val degreesPerItems = wheelItems.getDegreesPerItem()
        val size = min(this.maxHeight, this.maxWidth)

        wheelItems.forEachIndexed { index, item ->
            WheelSlice(
                modifier = Modifier.rotate(degrees = degreesPerItems * index),
                size = size,
                brush = item.color,
                degree = degreesPerItems,
                content = item.content
            )
        }
    }
}