package com.webxela.app.coupit.presentation.features.profile.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.presentation.features.home.component.ProfileIconPlaceHolder
import com.webxela.app.coupit.presentation.features.profile.viewmodel.ProfileUiEvent
import com.webxela.app.coupit.presentation.features.profile.viewmodel.ProfileUiState
import com.webxela.app.coupit.presentation.features.profile.viewmodel.ProfileViewModel
import com.webxela.app.coupit.presentation.theme.LARGE_CORNER_RADIUS
import com.webxela.app.coupit.presentation.theme.SMALL_CORNER_RADIUS
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    ProfileScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onNavigateToHome = onNavigateToHome
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    uiEvent: (ProfileUiEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(uiState.logoutResponse) {
        if (uiState.logoutResponse != null) onNavigateToHome()
    }

    LaunchedEffect(Unit) {
        uiEvent(ProfileUiEvent.GetProfileInfo)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(LARGE_CORNER_RADIUS),
        color = MaterialTheme.colorScheme.surface
    ) {
        if (uiState.isLoading) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            uiState.profileResponse?.let { merchant ->
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .animateContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(100.dp)
                    ) {
                        ProfileIconPlaceHolder(
                            profileName = merchant.businessName,
                            modifier = Modifier.size(100.dp),
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Text(
                        text = merchant.businessName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                        ),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ProfileInfoItem("Status", merchant.status)
                            ProfileInfoItem("Country", merchant.country)
                            ProfileInfoItem("Currency", merchant.currency)
                            ProfileInfoItem("Language", merchant.languageCode)
                            ProfileInfoItem("Created", merchant.createdAt)
                            ProfileInfoItem("Location ID", merchant.mainLocationId)
                        }
                    }

                    Button(
                        onClick = { uiEvent(ProfileUiEvent.Logout) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        shape = RoundedCornerShape(SMALL_CORNER_RADIUS)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.Logout,
                                contentDescription = "Logout",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = "Logout",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Medium
        )
    }
}