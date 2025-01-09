package com.webxela.app.coupit.presentation.wheel.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.presentation.wheel.viewmodel.WheelUiEvent
import com.webxela.app.coupit.presentation.wheel.viewmodel.WheelUiState
import com.webxela.app.coupit.presentation.wheel.viewmodel.WheelViewModel
import com.webxela.app.spinwheel.SpinWheel
import com.webxela.app.spinwheel.SpinWheelItem
import com.webxela.app.spinwheel.rememberSpinWheelState
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.seconds


@Composable
fun WheelScreenRoot(
    modifier: Modifier = Modifier,
    merchantId: String,
    transactionId: String,
    viewModel: WheelViewModel = koinViewModel()
) {

    val uiState by viewModel.wheelUiState.collectAsStateWithLifecycle()
    WheelScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        merchantId = merchantId,
        transactionId = transactionId
    )

}


@Composable
private fun WheelScreen(
    modifier: Modifier = Modifier,
    uiState: WheelUiState,
    uiEvent: (WheelUiEvent) -> Unit,
    merchantId: String,
    transactionId: String,
) {

    LaunchedEffect(Unit) {
        uiEvent(WheelUiEvent.CreateSession(merchantId, transactionId))
    }

    var dialogData by remember { mutableStateOf(OfferData()) }
    var dialogVisible by remember { mutableStateOf(false) }
    var spinItemTo by remember { mutableStateOf("") }
    LaunchedEffect(uiState.spinResponse) {
        if (uiState.spinResponse != null) {
            spinItemTo = uiState.spinResponse.data.offer.offerId
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
            if (uiState.errorMessage != null) Text(uiState.errorMessage)
            if (uiState.sessionResponse != null) {
                val wheelItems = uiState.sessionResponse.data.offers.mapIndexed { index, offer ->
                    val colors = listOf(
                        Color(0xFF3357FF),
                        Color(0xFFFF5733)
                    )
                    SpinWheelItem(
                        offerId = offer.offerId,
                        title = offer.title,
                        description = offer.description,
                        color = Brush.verticalGradient(
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

                val spinState = rememberSpinWheelState(
                    items = wheelItems,
                    onSpinningFinished = {
                        dialogVisible = true
                        val item = wheelItems.first { it.offerId == spinItemTo }
                        dialogData = OfferData(
                            offerId = item.offerId,
                            description = item.description,
                            title = item.title
                        )
                    },
                    stopNbTurn = 12f,
                    stopDuration = 10.seconds
                )

                LaunchedEffect(spinItemTo) {
                    if (spinItemTo.isNotBlank()) spinState.stopWheelAt(wheelItems.first { it.offerId == spinItemTo })
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
                                            merchantId = uiState.sessionResponse.data.merchantId,
                                            sessionId = uiState.sessionResponse.data.sessionId
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
            if (dialogVisible && dialogData.title != null) {
                AlertDialog(
                    onDismissRequest = {
                        dialogVisible = false
                        spinItemTo = ""
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            dialogVisible = false
                            spinItemTo = ""
                        }) {
                            Text("OK")
                        }
                    },
                    title = {
                        Text(
                            text = dialogData.title ?: "Spin Result",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                    text = {
                        Text(
                            text = dialogData.description ?: "",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                )
            }
        }
    }
}

private data class OfferData(
    val offerId: String? = null,
    val title: String? = null,
    val description: String? = null
)