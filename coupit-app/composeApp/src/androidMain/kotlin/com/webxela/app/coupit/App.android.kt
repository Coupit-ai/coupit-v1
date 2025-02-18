package com.webxela.app.coupit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.extensions.onCreateOrOnNewIntent
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.permission.permissionUtil
import dev.theolm.rinku.compose.ext.Rinku

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Notification permission
        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()
        NotifierManager.onCreateOrOnNewIntent(intent)
        enableEdgeToEdge()
        setContent {
            Rinku {
                App()
            }
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NotifierManager.onCreateOrOnNewIntent(intent)
    }
}

@Preview
@Composable
fun AppPreview() { App() }