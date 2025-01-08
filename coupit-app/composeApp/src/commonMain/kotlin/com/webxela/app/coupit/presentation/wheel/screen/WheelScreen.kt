package com.webxela.app.coupit.presentation.wheel.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.presentation.wheel.component.ResponsiveSpinWheel
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
    viewModel: WheelViewModel = koinViewModel()
) {

    val uiState by viewModel.wheelUiState.collectAsStateWithLifecycle()
    WheelScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )

}


@Composable
private fun WheelScreen(
    modifier: Modifier = Modifier,
    uiState: WheelUiState,
    uiEvent: (WheelUiEvent) -> Unit
) {

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

//        if (uiState.isLoading) CircularProgressIndicator()
//        if (uiState.errorMessage != null) Text(uiState.errorMessage)
//        if (uiState.sessionResponse != null) {
//        }

            val spinState = rememberSpinWheelState(
                items = wheelItems,
                onSpinningFinished = {},
                stopNbTurn = 20f,
                stopDuration = 20.seconds,
            )

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
                            .clickable{ spinState.stopWheelAt(wheelItems[1]) }
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

val wheelItems = listOf(
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFFFF5733), Color.White)), // Bright Orange
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    ),
    SpinWheelItem(
        color = Brush.verticalGradient(listOf(Color(0xFF3357FF), Color.White)), // Vibrant Blue
        content = {
//            Text(
//                text = "Item 1",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                style = MaterialTheme.typography.displayMedium
//            )
        }
    )
)