package com.webxela.app.coupit

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.webxela.app.coupit.koin.AppInitializer
import org.koin.android.ext.koin.androidContext

class CoupitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.initKoin() {
            androidContext(this@CoupitApp)
        }
        AppInitializer.startFcmListeners()
    }
}