package com.webxela.app.coupit.presentation.features.auth.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.presentation.component.LoadingButton
import com.webxela.app.coupit.presentation.features.auth.viewmodel.AuthUiEvent
import com.webxela.app.coupit.presentation.features.auth.viewmodel.AuthUiState
import com.webxela.app.coupit.presentation.features.auth.viewmodel.AuthViewModel
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.presentation.theme.LARGE_CORNER_RADIUS
import com.webxela.app.coupit.presentation.theme.SMALL_CORNER_RADIUS
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootAuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel(),
    token: String?,
    state: String?,
    error: String?,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.authUiState.collectAsStateWithLifecycle()

    AuthScreen(
        modifier = modifier,
        token = token,
        state = state,
        error = error,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun AuthScreen(
    modifier: Modifier = Modifier,
    token: String?,
    state: String?,
    error: String?,
    uiState: AuthUiState,
    uiEvent: (AuthUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
) {

    val uriHandler = LocalUriHandler.current
    val errorHandler = LocalErrorHandler.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            errorHandler.showError(it)
        }
    }

    LaunchedEffect(uiState.isLoading) {
        if (uiState.isLoading) uiEvent(AuthUiEvent.ResetErrorMessage)
    }

    LaunchedEffect(key1 = token, key2 = state, key3 = error) {
        uiEvent(AuthUiEvent.HandleOauthDeeplink(token, state, error))
    }

    LaunchedEffect(uiState.oauthFlowResponse) {
        if (uiState.oauthFlowResponse) onNavigateBack()
    }

    LaunchedEffect(uiState.connectionResponse) {
        uiState.connectionResponse?.redirectUri?.let { uri ->
            uriHandler.openUri(uri)
            uiEvent(AuthUiEvent.ResetConnectionResp)
        }
    }

    val buttonColor by animateColorAsState(
        targetValue = if (uiState.errorMessage.isNullOrBlank()) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.errorContainer
        },
        animationSpec = tween(500),
        label = "Button Color Animation"
    )

    val textColor by animateColorAsState(
        targetValue = if (uiState.errorMessage.isNullOrBlank()) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onErrorContainer
        },
        animationSpec = tween(500),
        label = "Text Color Animation"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth(.8f)
            .requiredSizeIn(minWidth = 350.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(LARGE_CORNER_RADIUS),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .padding(bottom = 16.dp)
            ) {}

            Text(
                text = "Login with Square",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Please click the button below to connect your Square account.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .alpha(.6f)
            )

            LoadingButton(
                onClick = { uiEvent(AuthUiEvent.ConnectWithSquare) },
                text = if (uiState.errorMessage.isNullOrBlank()) "Continue with Square"
                else "Failed, Try Again",
                loading = uiState.isLoading,
                shape = RoundedCornerShape(SMALL_CORNER_RADIUS),
                textStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = textColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}