package com.webxela.app.coupit

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.webxela.app.coupit.koin.platformModule
import com.webxela.app.coupit.koin.sharedModule
import com.webxela.app.coupit.presentation.features.firebase.FirebaseService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

expect fun initializeFcm()

object AppInitializer : KoinComponent {

    val firebaseService: FirebaseService by inject()

    fun initKoin(config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(sharedModule, platformModule)
        }
    }

    fun startFcmListeners() {
        initializeFcm()
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                firebaseService.handleNewToken(token)
            }

            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                firebaseService.handleNotificationPayload(data)
            }

            override fun onPayloadData(data: PayloadData) {
                super.onPayloadData(data)
                firebaseService.handleNotificationPayload(data)
            }
        })
    }
}