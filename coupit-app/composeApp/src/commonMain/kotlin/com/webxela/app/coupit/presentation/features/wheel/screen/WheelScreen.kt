package com.webxela.app.coupit.presentation.features.wheel.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.domain.model.SpinConfig
import com.webxela.app.coupit.presentation.component.ErrorDialog
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelUiEvent
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelUiState
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelViewModel
import com.webxela.app.spinwheel.SpinWheel
import com.webxela.app.spinwheel.SpinWheelItem
import com.webxela.app.spinwheel.rememberSpinWheelState
import com.webxela.app.spinwheel.util.AutoResizedText
import coupit.composeapp.generated.resources.Res
import coupit.composeapp.generated.resources.wheel_bg
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
    navigateToRewardScreen: (spinId: String) -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.wheelUiState.collectAsStateWithLifecycle()
    WheelScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        sessionId = sessionId,
        navigateToRewardScreen = navigateToRewardScreen,
        navigateBack = navigateBack
    )
}

@Composable
private fun WheelScreen(
    modifier: Modifier,
    uiState: WheelUiState,
    uiEvent: (WheelUiEvent) -> Unit,
    sessionId: String?,
    navigateToRewardScreen: (spinId: String) -> Unit,
    navigateBack: () -> Unit
) {
    var spinItemToId by remember { mutableStateOf("") }
    var spinId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        uiEvent(WheelUiEvent.GetWheelConfig(sessionId))
    }

    LaunchedEffect(uiState.spinResponse) {
        uiState.spinResponse?.let {
            spinItemToId = it.reward.id
            spinId = it.id
        }
    }

    uiState.errorMessage?.let {
        ErrorDialog(
            errorMessage = it,
            onDismiss = {
                uiEvent(WheelUiEvent.ClearErrorMessage)
                navigateBack()
            }
        )
    }
    Scaffold { innerPadding ->
        Box(modifier = modifier.fillMaxSize()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .aspectRatio(1f)
                    ) {
                        SpinWheel(spinWheelState = spinState) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        if (!spinState.isSpinning()) {
                                            uiEvent(
                                                WheelUiEvent.PerformSpin(
                                                    uiState.spinConfigResponse.session.id
                                                )
                                            )
                                        }
                                    }
                            ) {
                                AutoResizedText(
                                    text = "SPIN",
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun SpinConfig.toSpinWheelItems(): List<SpinWheelItem> {
    val requiredCount = 12
    val newRewards = if (rewards.size >= requiredCount) rewards else {
        val duplicated = mutableListOf<SpinConfig.Reward>()
        while (duplicated.size < requiredCount) duplicated.addAll(rewards)
        duplicated.take(requiredCount)
    }
    val totalRewards = newRewards.size
    val numColors = max(2, (totalRewards + 1) / 2)
    val baseColors = listOf(
        Color(0xFF40BDCA),
        Color(0xFFCB655F),
        Color(0xFFFEC93D)
    )
    val colorsToUse = baseColors.take(min(numColors, baseColors.size))
    return newRewards.mapIndexed { i, r ->
        SpinWheelItem(
            identifier = r.id,
            brush = Brush.linearGradient(
                listOf(
                    colorsToUse[i % colorsToUse.size],
                    colorsToUse[i % colorsToUse.size]
                )
            ),
            title = r.title
        )
    }
}