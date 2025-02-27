package com.webxela.app.coupit.presentation.features.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import com.webxela.app.coupit.presentation.component.AutoResizedText

@Composable
fun ProfileIconPlaceHolder(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    profileName: String,
    style: TextStyle = MaterialTheme.typography.titleLarge
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        AutoResizedText(
            profileName.capitalize(Locale.current).first().toString(),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = style.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        )
    }
}