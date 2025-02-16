package com.webxela.app.coupit.presentation.features.reward.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar
import com.webxela.app.coupit.presentation.features.reward.component.RewardDialog
import com.webxela.app.coupit.presentation.features.reward.component.RewardsCard
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiEvent
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiState
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun RewardManagerScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel = koinViewModel(),
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

    val gridState = rememberLazyGridState()
    val errorHandler = LocalErrorHandler.current

    LaunchedEffect(uiState.rewardResponse) {
        uiEvent(RewardUiEvent.GetAllRewards)
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            errorHandler.showError(error)
        }
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedReward by remember { mutableStateOf<Reward?>(null) }

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
        topBar = { SecondaryTopAppBar(title = "Rewards Manager") },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    selectedReward = null
                    showDialog = true
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (uiState.isLoading) CircularProgressIndicator()
            else
                LazyVerticalGrid(
                    state = gridState,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(24.dp),
                    columns = GridCells.Adaptive(300.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    items(
                        uiState.allRewardResponse,
                        key = { it.id!! }
                    ) { reward ->
                        RewardsCard(
                            reward = reward,
                            onCardClick = {
                                selectedReward = reward
                                showDialog = true
                            },
                            onDelete = {
//                                uiEvent(RewardUiEvent.DeleteReward(reward))
                            }
                        )
                    }
                }
        }
    }
}