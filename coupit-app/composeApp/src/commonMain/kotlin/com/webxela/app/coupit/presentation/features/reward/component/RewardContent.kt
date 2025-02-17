package com.webxela.app.coupit.presentation.features.reward.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardUiState
import kotlinx.datetime.Instant

@Composable
fun RewardContent(
    modifier: Modifier,
    padding: PaddingValues,
    uiState: RewardUiState,
    gridState: LazyGridState,
    onRewardClick: (Reward) -> Unit,
    onDeleteReward: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyVerticalGrid(
                state = gridState,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(24.dp),
                columns = GridCells.Adaptive(300.dp),
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                items(
                    uiState.allRewardResponse.sortedByDescending { reward ->
                        Instant.parse(reward.createdAt!!)
                    },
                    key = { it.id!! }
                ) { reward ->
                    RewardsCard(
                        modifier = Modifier.animateItem(),
                        reward = reward,
                        onCardClick = { onRewardClick(reward) },
                        onDelete = { onDeleteReward(reward.id!!) }
                    )
                }
            }
        }
    }
}