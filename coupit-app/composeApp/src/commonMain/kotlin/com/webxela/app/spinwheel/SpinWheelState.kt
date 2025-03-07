package com.webxela.app.spinwheel

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import co.touchlab.kermit.Logger
import com.webxela.app.spinwheel.util.getDegreeFromSectionWithRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Stable
data class SpinWheelState(
    internal val wheelItems: List<SpinWheelItem>,
    private val stopDuration: Duration,
    private val stopNbTurn: Float,
    private val rotationPerSecond: Float,
    private val scope: CoroutineScope,
    private val onSpinningFinished: (() -> Unit)? = null,
) {
    internal val rotation = Animatable(0f)
    private var isSpinning = false

    fun isSpinning(): Boolean = isSpinning

    fun stopWheelAt(identifier: String) {
        if (isSpinning) return

        val identifiers = wheelItems.map { it.identifier }
        val sectionToStop = identifiers.indexOf(identifier).takeIf { it != -1 }
            ?: throw IndexOutOfBoundsException("Section $identifiers does not exist.")

        scope.launch {
            isSpinning = true
            val destinationDegree = getDegreeFromSectionWithRandom(wheelItems, sectionToStop)

            rotation.animateTo(
                targetValue = rotation.value + stopNbTurn * 360f + destinationDegree + (360f - (rotation.value % 360f)),
                animationSpec = tween(
                    durationMillis = stopDuration.inWholeMilliseconds.toInt(),
                    easing = EaseOutCubic
                )
            )
            isSpinning = false
            onSpinningFinished?.invoke()
        }
    }
}

@Composable
fun rememberSpinWheelState(
    items: List<SpinWheelItem>,
    stopDuration: Duration = 8.seconds,
    stopNbTurn: Float = 3f,
    rotationPerSecond: Float = 0.8f,
    scope: CoroutineScope = rememberCoroutineScope(),
    onSpinningFinished: (() -> Unit)?
): SpinWheelState {
    return remember {
        SpinWheelState(
            wheelItems = items,
            stopDuration = stopDuration,
            stopNbTurn = stopNbTurn,
            rotationPerSecond = rotationPerSecond,
            scope = scope,
            onSpinningFinished = onSpinningFinished,
        )
    }
}