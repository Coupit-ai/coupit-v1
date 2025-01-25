package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.api.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.api.mappper.PaymentDtoMapper.toPayment
import com.webxela.backend.coupit.config.SquareConfig
import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infra.external.repo.OauthDataSourceAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.PaymentRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.SessionRepoAdapter
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
    private val utilityService: UtilityService
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
                    sendFcmNotification(sessionId)
                } ?: logger.error("failed to send FCM notification")
            } else {
                logger.error("Received invalid payment webhook")
                return
            }
        } catch (ex: Exception) {
            logger.error("Failed to handle payment webhook", ex)
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
            logger.error("Failed to save payment and create session", ex)
            return null
        }
    }

    private fun sendFcmNotification(sessionId: UUID) {
        // Send FCM notification to merchant
        logger.info("Sending FCM notification")
    }
}