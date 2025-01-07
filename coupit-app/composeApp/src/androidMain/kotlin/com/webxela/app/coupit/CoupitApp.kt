package com.webxela.app.coupit

import android.app.Application
import com.webxela.app.coupit.koin.KoinInitialiser
import org.koin.android.ext.koin.androidContext

class CoupitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitialiser.initKoin() {
            androidContext(this@CoupitApp)
        }
    }
}