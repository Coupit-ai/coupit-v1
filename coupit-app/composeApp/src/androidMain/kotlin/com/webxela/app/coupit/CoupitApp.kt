package com.webxela.app.coupit

import android.app.Application
import com.webxela.app.coupit.koin.KoinInitializer
import org.koin.android.ext.koin.androidContext

class CoupitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer.initKoin() {
            androidContext(this@CoupitApp)
        }
    }
}