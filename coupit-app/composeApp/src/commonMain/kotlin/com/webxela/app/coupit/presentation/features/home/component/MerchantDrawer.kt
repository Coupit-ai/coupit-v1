package com.webxela.app.coupit.presentation.features.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.domain.model.Merchant
import com.webxela.app.coupit.presentation.features.home.viewmodel.DrawerItem


@Composable
fun MerchantDrawer(
    modifier: Modifier = Modifier,
    drawerItems: List<DrawerItem>,
    selectedItem: DrawerItem,
    merchant: Merchant?,
    isLoading: Boolean,
    onItemSelect: (DrawerItem) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {
        Column {
            ListItem(
                headlineContent = {
                    Text(
                        text = "Coupit",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.DeviceHub,
                        contentDescription = "Avatar"
                    )
                },
                trailingContent = {
                    if (isLoading)
                        CircularProgressIndicator(
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(24.dp)
                        )
                }
            )
            HorizontalDivider()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            drawerItems.forEach { item ->
                NavigationDrawerItem(
                    shape = RoundedCornerShape(12.dp),
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = selectedItem == item,
                    onClick = { onItemSelect(item) },
                    modifier = Modifier.height(50.dp)
                )
            }
        }

        Column {
            HorizontalDivider()
            ListItem(
                leadingContent = {
                    ProfileIconPlaceHolder(
                        profileName = merchant?.businessName ?: "X",
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
                }
            )
        }
    }

}