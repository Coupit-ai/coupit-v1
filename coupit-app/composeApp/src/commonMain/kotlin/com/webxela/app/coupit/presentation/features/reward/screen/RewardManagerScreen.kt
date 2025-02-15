package com.webxela.app.coupit.presentation.features.reward.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar
import com.webxela.app.coupit.presentation.features.reward.component.RewardsCard


@Composable
fun RewardManagerScreenRoot(modifier: Modifier = Modifier) {
    RewardManagerScreen(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RewardManagerScreen(modifier: Modifier = Modifier) {

    val gridState = rememberLazyGridState()

    Scaffold(
        topBar = { SecondaryTopAppBar(title = "Rewards Manager") },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { /*TODO*/ },
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
            items(20) {
                RewardsCard() {

                }
            }
        }
    }
}