package com.webxela.app.coupit.presentation.features.wheel.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.domain.model.SpinConfig
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelUiEvent
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelUiState
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelViewModel
import com.webxela.app.spinwheel.SpinWheel
import com.webxela.app.spinwheel.SpinWheelItem
import com.webxela.app.spinwheel.rememberSpinWheelState
import coupit.composeapp.generated.resources.Res
import coupit.composeapp.generated.resources.ic_cyclone
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds

@Composable
fun WheelScreenRoot(
    modifier: Modifier = Modifier,
    sessionId: String?,
    viewModel: WheelViewModel = koinViewModel(),
    navigateToRewardScreen: (String) -> Unit
) {
    val uiState by viewModel.wheelUiState.collectAsStateWithLifecycle()
    WheelScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        sessionId = sessionId,
        navigateToRewardScreen = navigateToRewardScreen
    )
}

@Composable
private fun WheelScreen(
    modifier: Modifier,
    uiState: WheelUiState,
    uiEvent: (WheelUiEvent) -> Unit,
    sessionId: String?,
    navigateToRewardScreen: (String) -> Unit
) {
    val errorHandler = LocalErrorHandler.current
    var spinItemToId by remember { mutableStateOf("") }
    var spinId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        uiEvent(WheelUiEvent.GetWheelConfig(sessionId))
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorHandler.showError(it) }
    }

    LaunchedEffect(uiState.spinResponse) {
        uiState.spinResponse?.let {
            spinItemToId = it.reward.id
            spinId = it.id
        }
    }

    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize().padding(innerPadding)
        ) {
            if (uiState.isLoading) CircularProgressIndicator()
            if (uiState.spinConfigResponse != null) {
                val wheelItems = uiState.spinConfigResponse.toSpinWheelItems()
                val spinState = rememberSpinWheelState(
                    items = wheelItems,
                    onSpinningFinished = {
                        navigateToRewardScreen(spinId)
                        spinItemToId = ""
                    },
                    stopNbTurn = 15f,
                    stopDuration = 10.seconds
                )
                LaunchedEffect(spinItemToId) {
                    if (spinItemToId.isNotEmpty()) spinState.stopWheelAt(spinItemToId)
                }
                Box(
                    modifier = modifier
                        .fillMaxSize(0.5f)
                        .aspectRatio(1f)
                ) {
                    SpinWheel(
                        outerRingColor = MaterialTheme.colorScheme.error,
                        spinWheelState = spinState
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_cyclone),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clickable {
                                    uiEvent(
                                        WheelUiEvent.PerformSpin(
                                            uiState.spinConfigResponse.session.id
                                        )
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}

fun SpinConfig.toSpinWheelItems(): List<SpinWheelItem> {
    val requiredCount = 12
    val newRewards = if (rewards.size >= requiredCount) rewards else {
        val duplicated = mutableListOf<SpinConfig.Reward>()
        while (duplicated.size < requiredCount) duplicated.addAll(rewards)
        duplicated.take(requiredCount)
    }
    val totalRewards = newRewards.size
    val numColors = max(2, (totalRewards + 1) / 2)
    val baseColors = listOf(
        Color(0xFF3BAF69), Color(0xFFEFC25F), Color(0xFF678CC8), Color(0xFFDB5A5A)
    )
    val colorsToUse = baseColors.take(min(numColors, baseColors.size))
    return newRewards.mapIndexed { i, r ->
        SpinWheelItem(
            identifier = r.id,
            brush = Brush.linearGradient(listOf(colorsToUse[i % colorsToUse.size], colorsToUse[i % colorsToUse.size])),
            title = r.title
        )
    }
}