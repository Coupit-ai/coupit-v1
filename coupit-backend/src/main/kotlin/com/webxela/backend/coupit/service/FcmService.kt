package com.webxela.backend.coupit.service

import com.google.firebase.messaging.*
import com.webxela.backend.coupit.rest.dto.FcmTokenRequest
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import com.webxela.backend.coupit.utils.AppConstants
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FcmService(
    private val userRepo: UserRepoAdapter,
    private val utilityService: UtilityService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(FcmService::class.java)
    }

    @Transactional(readOnly = false)
    fun registerFcmToken(request: FcmTokenRequest): String {
        logger.info("Registering FCM Token")
        utilityService.getCurrentLoginUser()?.let { user ->
            val tokenUpdated = userRepo.updateFcmToken(user.username, request.token)
            val devTypeUpdated = userRepo.updateDeviceType(user.username, request.deviceType)
            if (tokenUpdated && devTypeUpdated) {
                logger.info("Fcm token updated")
                return request.token
            } else run {
                throw ApiError.InternalError("Failed while registering FCM token")
            }
        } ?: throw ApiError.BadRequest("You are not authorized to perform this action")
    }


    fun sendFcmNotification(
        token: String,
        title: String,
        body: String,
        sessionId: String
    ) {

        val notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build()

        // Android config
        val androidConfig = AndroidConfig.builder()
            .setPriority(AndroidConfig.Priority.HIGH)
            .setTtl(AppConstants.FCM_TIMEOUT)
            .setNotification(
                AndroidNotification.builder()
                    .setSound("default")
                    .build()
            )
            .build()

        // IOS config
        val apnsConfig = ApnsConfig.builder()
            .setAps(
                Aps.builder()
                    .setSound("default")
                    .build()
            )
            .build()

        val message = Message.builder()
            .setToken(token)
            .setNotification(notification)
            .putData("sessionId", sessionId) // Custom parameter
            .setAndroidConfig(androidConfig)
            .setApnsConfig(apnsConfig)
            .build()

        try {
            val response = FirebaseMessaging.getInstance().send(message)
            logger.info("Successfully sent reward notification: $response")
        } catch (ex: Exception) {
            logger.error("Error sending reward notification", ex)
            throw ApiError.BadRequest("Failed to send reward notification")
        }
    }


}