package com.webxela.app.coupit.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.Menu
import coupit.composeapp.generated.resources.Res
import coupit.composeapp.generated.resources.ic_qrcode_scanner
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateToScannerScreen: () -> Unit
) {
    TopAppBar(
        title = {
            Text("Coupit")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = FeatherIcons.Menu,
                    contentDescription = "Qr Code Scanner"
                )
            }
        },
        actions = {
            IconButton(onClick = onNavigateToScannerScreen) {
                Icon(
                    painter = painterResource(Res.drawable.ic_qrcode_scanner),
                    contentDescription = "Qr Code Scanner"
                )
            }
        }
    )
}