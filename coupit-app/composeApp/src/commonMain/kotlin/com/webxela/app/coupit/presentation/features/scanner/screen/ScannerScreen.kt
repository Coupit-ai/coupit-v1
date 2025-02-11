package com.webxela.app.coupit.presentation.features.scanner.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import qrscanner.CameraLens
import qrscanner.QrScanner

@Composable
fun ScannerScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToRewardScreen: (spinId: String) -> Unit,
    onNavigateBack: () -> Unit
) {

    ScannerScreen(
        modifier = modifier,
        onNavigateToRewardScreen = onNavigateToRewardScreen,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScannerScreen(
    modifier: Modifier = Modifier,
    onNavigateToRewardScreen: (spinId: String) -> Unit,
    onNavigateBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    var hasNavigated by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets(0,0,0,0),
        topBar = {
            SecondaryTopAppBar(
                title = "Scan QR Code",
                onNavigateBack = onNavigateBack,
            )
        }
    ) { innerPadding ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            QrScanner(
                modifier = Modifier,
                flashlightOn = false,
                customOverlay = {
                    this.drawQRCodeScannerOverlay()
                },
                cameraLens = CameraLens.Back,
                openImagePicker = false,
                onFailure = {
                    Logger.e("error occur $it")
                },
                onCompletion = {
                    if (hasNavigated.not()) {
                        coroutineScope.launch {
                            delay(500L)
                            onNavigateToRewardScreen("6676767676767676")
                        }
                        hasNavigated = true
                    }
                },
                imagePickerHandler = {

                }
            )
        }
    }
}


fun DrawScope.drawQRCodeScannerOverlay(
    cornerRadius: Dp = 16.dp,
    borderColor: Color = Color.White,
    borderWidth: Dp = 4.dp,
    overlayColor: Color = Color.Black.copy(alpha = 0.5f)
) {
    val boxSize = kotlin.math.min(size.width, size.height) * 0.6f
    val cornerRadiusPx = cornerRadius.toPx()
    val borderWidthPx = borderWidth.toPx()

    // Calculate the top-left corner of the transparent square
    val left = (size.width - boxSize) / 2
    val top = (size.height - boxSize) / 2

    // Create a path for the transparent box
    val transparentBoxPath = Path().apply {
        addRoundRect(
            RoundRect(
                left = left,
                top = top,
                right = left + boxSize,
                bottom = top + boxSize,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
        )
    }

    // Draw semi-transparent overlay excluding the transparent box area
    clipPath(transparentBoxPath, clipOp = ClipOp.Difference) {
        drawRect(
            color = overlayColor,
            size = size
        )
    }

    // Draw border around the transparent square
    drawRoundRect(
        color = borderColor,
        topLeft = Offset(left, top),
        size = Size(boxSize, boxSize),
        cornerRadius = CornerRadius(cornerRadiusPx),
        style = Stroke(width = borderWidthPx)
    )
}