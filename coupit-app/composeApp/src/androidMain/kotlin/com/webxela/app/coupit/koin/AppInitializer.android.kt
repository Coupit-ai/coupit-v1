package com.webxela.app.coupit.koin

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.webxela.app.coupit.R

actual fun initializeFcm() {
    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Android(
            notificationIconResId = R.mipmap.ic_launcher,
            showPushNotification = true
        )
    )
}