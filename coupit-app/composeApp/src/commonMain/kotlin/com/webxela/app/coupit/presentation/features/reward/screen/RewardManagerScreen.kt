package com.webxela.app.coupit.presentation.features.reward.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar
import com.webxela.app.coupit.presentation.features.reward.component.RewardContent
import com.webxela.app.coupit.presentation.features.reward.component.RewardDialog
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiEvent
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiState
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun RewardManagerScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel = koinViewModel()
) {
    val uiState by viewModel.rewardUiState.collectAsStateWithLifecycle()
    RewardManagerScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RewardManagerScreen(
    modifier: Modifier = Modifier,
    uiState: RewardUiState,
    uiEvent: (RewardUiEvent) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedReward by remember { mutableStateOf<Reward?>(null) }
    val gridState = rememberLazyGridState()
    val errorHandler = LocalErrorHandler.current

    LaunchedEffect(uiState.rewardResponse, uiState.rewardDeleteResponse) {
        uiEvent(RewardUiEvent.GetAllRewards)
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            errorHandler.showError(it)
            uiEvent(RewardUiEvent.ClearErrorMessage)
        }
    }

    if (showDialog) {
        RewardDialog(
            reward = selectedReward,
            onDismiss = {
                showDialog = false
                selectedReward = null
            },
            onSaveClicked = { reward ->
                Logger.e("Reward: $reward")
                uiEvent(RewardUiEvent.CreateReward(reward))
            }
        )
    }

    Scaffold(
        topBar = { SecondaryTopAppBar(title = "Rewards") },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        floatingActionButton = {
            AddRewardButton(
                onClick = {
                    selectedReward = null
                    showDialog = true
                }
            )
        }
    ) { padding ->
        RewardContent(
            modifier = modifier,
            padding = padding,
            uiState = uiState,
            gridState = gridState,
            onRewardClick = { reward ->
                selectedReward = reward
                showDialog = true
            },
            onDeleteReward = { rewardId ->
                uiEvent(RewardUiEvent.DeleteReward(rewardId))
            }
        )
    }
}

@Composable
private fun AddRewardButton(onClick: () -> Unit) {
    LargeFloatingActionButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            modifier = Modifier.size(50.dp)
        )
    }
}