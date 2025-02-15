package com.webxela.app.coupit.presentation.features.reward.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun RewardsCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.aspectRatio(3f/1.5f)
    ) {

    }
}