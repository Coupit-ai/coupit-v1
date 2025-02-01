package com.webxela.app.coupit.presentation.features.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DesktopMac
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.FlutterDash
import androidx.compose.material.icons.filled.InstallDesktop
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ScreenSearchDesktop
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.presentation.features.home.viewmodel.DrawerItem
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.brands.AppStore
import compose.icons.fontawesomeicons.brands.Slack
import compose.icons.fontawesomeicons.regular.Images
import compose.icons.fontawesomeicons.regular.UserCircle


@Composable
fun MerchantDrawer(
    modifier: Modifier = Modifier,
    drawerItems: List<DrawerItem>,
    selectedItem: DrawerItem,
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
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = "Avatar"
                    )
                },
                headlineContent = {
                    Text(text = "Aman Verma")
                },
                supportingContent = {
                    Text(
                        text = "akverma4aman@gmail.com",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }

}