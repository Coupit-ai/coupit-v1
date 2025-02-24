package com.webxela.app.coupit

import android.app.Application
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