package com.webxela.app.coupit.presentation.features.firebase

import co.touchlab.kermit.Logger
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.webxela.app.coupit.core.domain.getDeviceType
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.domain.usecase.FirebaseUseCase
import dev.theolm.rinku.Rinku
import kotlinx.coroutines.runBlocking

class FirebaseService(private val firebaseUseCase: FirebaseUseCase) {

    fun handleNewToken(token: String) {
        Logger.i("New FCM token created: $token")
        runBlocking {
            firebaseUseCase.updateNewToken(token, getDeviceType())
        }
    }

    fun handleNotificationPayload(data: PayloadData) {
        NotifierManager.getLocalNotifier().removeAll()
        Logger.i("Notification clicked, Notification payloadData: $data")
        data.let { payload ->
            val sessionId: String = payload.getValue("sessionId").toString()
            Rinku.handleDeepLink(
                url = "${AppConstant.DEEPLINK_URL}/nav/wheel?sessionId=$sessionId"
            )
        }
    }

}