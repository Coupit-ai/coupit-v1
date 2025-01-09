package com.webxela.app.coupit.presentation.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import compose.icons.AllIcons
import compose.icons.FeatherIcons
import compose.icons.feathericons.Menu
import compose.icons.feathericons.Navigation
import coupit.composeapp.generated.resources.Res
import coupit.composeapp.generated.resources.ic_qrcode_scanner
import org.jetbrains.compose.resources.painterResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToWheelScreen: (
        merchantId: String,
        transactionId: String
    ) -> Unit
) {

    HomeScreen(onNavigateToWheelScreen = onNavigateToWheelScreen)
}

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToWheelScreen: (
        merchantId: String,
        transactionId: String
    ) -> Unit
) {

    // HardCoding Merchant and Transaction Id for now
    val merchantId = "1234567890"
    val transactionId = Uuid.random().toString()

    Scaffold(
        topBar = {
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
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_qrcode_scanner),
                            contentDescription = "Qr Code Scanner"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            Text("Home Screen")
            Spacer(modifier.height(16.dp))
            Text("Merchant Id: $merchantId")
            Spacer(modifier.height(16.dp))
            Text(
                "Random Transaction Id: $transactionId",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier.height(16.dp))

            Button(
                onClick = { onNavigateToWheelScreen(merchantId, transactionId) }
            ) {
                Text("Start Test")
            }
        }

    }
}