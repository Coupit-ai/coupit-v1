package com.webxela.app.coupit.presentation.features.reward.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiEvent
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiState
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardViewModel
import coupit.composeapp.generated.resources.Res
import coupit.composeapp.generated.resources.ic_cyclone
import coupit.composeapp.generated.resources.ic_qrcode_scanner
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrLogoPadding
import io.github.alexzhirkevich.qrose.options.QrLogoShape
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.brush
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RewardScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel = koinViewModel(),
    spinId: String,
    onNavigateBack: () -> Unit
) {

    val uiState by viewModel.rewardUiState.collectAsStateWithLifecycle()

    RewardScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        spinId = spinId,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RewardScreen(
    modifier: Modifier = Modifier,
    uiState: RewardUiState,
    uiEvent: (RewardUiEvent) -> Unit,
    spinId: String,
    onNavigateBack: () -> Unit
) {

    val errorHandler = LocalErrorHandler.current

    LaunchedEffect(Unit) {
        uiEvent(RewardUiEvent.GetSpinResult(spinId))
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            errorHandler.showError(error)
        }
    }

    Scaffold(
        topBar = {
           SecondaryTopAppBar(
               title = "Reward",
               onNavigateBack = onNavigateBack
           )
        }
    ) { innerPadding ->

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            if (uiState.isLoading) CircularProgressIndicator()
            if (uiState.spinResponse != null) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    val logoPainter = painterResource(Res.drawable.ic_qrcode_scanner)

                    val qrcodePainter : Painter = rememberQrCodePainter(uiState.spinResponse.qrCode) {
                        logo {
                            painter = logoPainter
                            padding = QrLogoPadding.Natural(.1f)
                            shape = QrLogoShape.circle()
                            size = 0.2f
                        }

                        shapes {
                            ball = QrBallShape.circle()
                            darkPixel = QrPixelShape.roundCorners()
                            frame = QrFrameShape.roundCorners(.25f)
                        }
                    }

                    Image(
                        painter = qrcodePainter,
                        contentDescription = "Your Offer",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.size(200.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = uiState.spinResponse.reward.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = uiState.spinResponse.reward.description,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}