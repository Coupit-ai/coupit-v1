package com.webxela.app.coupit.presentation.features.wheel.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.seconds


@Composable
fun WheelScreenRoot(
    modifier: Modifier = Modifier,
    sessionId: String?,
    viewModel: WheelViewModel = koinViewModel(),
    navigateToRewardScreen: (spinId: String) -> Unit
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
    modifier: Modifier = Modifier,
    uiState: WheelUiState,
    uiEvent: (WheelUiEvent) -> Unit,
    sessionId: String?,
    navigateToRewardScreen: (spinId: String) -> Unit
) {

    val errorHandler = LocalErrorHandler.current

    var spinItemToId by remember { mutableStateOf("") }
    var spinId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        uiEvent(WheelUiEvent.GetWheelConfig(sessionId))
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            errorHandler.showError(it)
        }
    }

    LaunchedEffect(uiState.spinResponse) {
        if (uiState.spinResponse != null) {
            spinItemToId = uiState.spinResponse.reward.id
            spinId = uiState.spinResponse.id
        }
    }

    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
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
                    if (spinItemToId.isNotEmpty()) {
                        spinState.stopWheelAt(spinItemToId)
                    }
                }

                Box(
                    modifier = modifier
                        .fillMaxSize(.5f)
                        .aspectRatio(1f)
                ) {
                    SpinWheel(spinWheelState = spinState) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    uiEvent(
                                        WheelUiEvent.PerformSpin(
                                            sessionId = uiState.spinConfigResponse.session.id
                                        )
                                    )
                                }
                        ) {
                            Text(
                                text = "Spin",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}


fun SpinConfig.toSpinWheelItems(): List<SpinWheelItem> {
    val colors = listOf(
        Color(0xFF3357FF),
        Color(0xFFFF5733)
    )
    return this.reward.mapIndexed { index, offer ->
        SpinWheelItem(
            identifier = offer.id,
            brush = Brush.verticalGradient(
                listOf(colors[index % colors.size], Color.White)
            ),
            content = {
                Text(
                    text = offer.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.width(120.dp)
                )
            }
        )
    }
}