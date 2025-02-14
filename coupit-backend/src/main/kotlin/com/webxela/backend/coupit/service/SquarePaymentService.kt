package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.rest.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.rest.mappper.PaymentDtoMapper.toPayment
import com.webxela.backend.coupit.config.SquareConfig
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infra.external.repo.OauthDataSourceAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.PaymentRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.SessionRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import com.webxela.backend.coupit.utils.AppConstants
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SquarePaymentService(
    private val oauthDataSource: OauthDataSourceAdapter,
    private val merchantRepo: MerchantRepoAdapter,
    private val paymentRepo: PaymentRepoAdapter,
    private val sessionRepo: SessionRepoAdapter,
    private val squareConfig: SquareConfig,
    private val utilityService: UtilityService,
    private val fcmService: FcmService,
    private val userRepo: UserRepoAdapter
) {

    companion object {
        private val logger = LogManager.getLogger(SquarePaymentService::class.java)
    }

    @Transactional
    fun handlePaymentWebhook(
        requestBody: PaymentWebhookRequest,
        signature: String
    ) {
        try {
//            val mapper = jacksonObjectMapper()
//            val stringifiedBody = mapper.writeValueAsString(requestBody)
//
//            val isFromSquare = oauthDataSource.isWebhookFromSquare(
//                stringifiedBody, signature,
//                squareConfig.revokeWebhookUrl,
//                squareConfig.revokeOauthSign
//            )
            // We are not verifying the signature for now
            val isFromSquare = true
            if (isFromSquare) {
                logger.info("Received payment webhook from square")

                savePaymentAndCreateSession(requestBody)?.let { sessionId ->
                    sendFcmNotification(sessionId, requestBody.merchantId)
                } ?: logger.error("failed to send FCM notification")

            } else {
                logger.error("Received invalid payment webhook")
                return
            }
        } catch (ex: Exception) {
            logger.error("Failed to handle payment webhook: ${ex.message}", ex)
        }
    }

    @Transactional(readOnly = false)
    fun savePaymentAndCreateSession(requestBody: PaymentWebhookRequest): UUID? {
        try {

            sessionRepo.getSessionByPaymentId(requestBody.data.objectX.payment.id)?.let {
                logger.error("A session associated with this webhook already exists")
                return null
            }

            utilityService.getUserIfExistsOrConnected(requestBody.merchantId) ?: run {
                logger.error("Either user is logged out or not connected")
                return null
            }

            val merchant = merchantRepo.getMerchant(requestBody.merchantId) ?: run {
                logger.error("The merchant in payment webhook does not exist in our DB")
                return null
            }

            val payment = paymentRepo.savePayment(requestBody.toPayment(merchant)) ?: run {
                logger.error("Cant save payment info to DB")
                return null
            }

            val session = SpinSession(
                merchant = merchant,
                payment = payment
            ).let { sessionRepo.createSession(it) }

            return session?.id

        } catch (ex: Exception) {
            logger.error("Failed to save payment and create session: ${ex.message}", ex)
            return null
        }
    }

    private fun sendFcmNotification(sessionId: UUID, merchantId: String) {
        logger.info("Sending FCM notification")

        userRepo.getUserByEmail(merchantId)?.let { user ->
            user.fcmToken ?: run {
                logger.error("User $merchantId does not have a fcm token")
                throw ApiError.InternalError("User $merchantId does not have a fcm token")
            }

            fcmService.sendFcmNotification(
                sessionId = sessionId.toString(),
                token = user.fcmToken,
                title = AppConstants.PAYMENT_FCM_TITLE,
                body = AppConstants.PAYMENT_FCM_BODY
            )
        } ?: run {
            logger.error("User with id $merchantId not found")
            throw ApiError.InternalError("User with id $merchantId not found")
        }
    }
}