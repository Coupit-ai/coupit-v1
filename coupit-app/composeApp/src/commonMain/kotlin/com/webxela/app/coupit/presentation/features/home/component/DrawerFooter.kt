package com.webxela.app.coupit.presentation.features.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.domain.model.Merchant


@Composable
fun DrawerFooter(
    modifier: Modifier = Modifier,
    merchant: Merchant?,
    onNavigateToProfile: () -> Unit
) {
    Column {
        HorizontalDivider()
        ListItem(
            leadingContent = {
                ProfileIconPlaceHolder(
                    profileName = merchant?.businessName ?: "x",
                    modifier = Modifier.size(40.dp)
                )
            },
            headlineContent = {
                Text(
                    text = merchant?.businessName ?: "Please Login",
                    fontWeight = FontWeight.Medium
                )
            },
            supportingContent = {
                Text(
                    text = merchant?.id ?: "You need to login first!",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            trailingContent = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.MoreVert, "More Options")
                }
            },
            modifier = modifier.clickable { onNavigateToProfile() }
        )
    }
}