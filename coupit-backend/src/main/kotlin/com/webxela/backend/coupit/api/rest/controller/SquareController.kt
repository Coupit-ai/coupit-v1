package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.api.rest.dto.auth.RevokeWebhookRequest
import com.webxela.backend.coupit.api.rest.dto.auth.RedirectResponse
import com.webxela.backend.coupit.api.rest.dto.auth.LogOutResponse
import com.webxela.backend.coupit.application.service.SquareOauthManager
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder

@RestController
@RequestMapping("/api/v1/square")
class SquareController(private val squareOauthManager: SquareOauthManager) {

    companion object {
        private val logger = LogManager.getLogger(SquareController::class.java)
    }

    @GetMapping("/oauth/connect")
    fun loginToSquare(): ResponseEntity<ApiResponse<RedirectResponse>> {
        val loginResponse = RedirectResponse(
            redirectUri = squareOauthManager.buildSquareOauthUrl(),
            message = "Follow this url to start oauth flow"
        )
        return ResponseEntity.ok(ApiResponse.success(loginResponse))
    }

    @GetMapping("/oauth/callback")
    fun handleLoginCallback(
        @RequestParam("code", required = false) code: String?,
        @RequestParam("error", required = false) error: String?,
    ): ResponseEntity<String> {
        var redirectUri = "coupit://callback?"
        redirectUri += if (error != null || code == null) {
            "error=${URLEncoder.encode(error ?: "Authorization failed", "UTF-8")}"
        } else {
            try {
                val jwtToken = squareOauthManager.processSquareOauthCallback(code)
                jwtToken?.let {
                    if (redirectUri.contains("?")) {
                        "&token=${URLEncoder.encode(jwtToken, "UTF-8")}"
                    } else {
                        "token=${URLEncoder.encode(jwtToken, "UTF-8")}"
                    }
                }
            } catch (ex: Exception) {
                "error=${URLEncoder.encode(ex.message ?: "Token generation failed", "UTF-8")}"
            }
        }
        logger.info("Redirecting oauth flow to $redirectUri")
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", redirectUri).build()
    }

    @PostMapping("/webhook/revoke")
    fun handleLogoutWebhook(
        @RequestBody requestBody: RevokeWebhookRequest,
        @RequestHeader("x-square-hmacsha256-signature") signature: String
    ): ResponseEntity<Nothing> {
        if (requestBody.data?.type == "revocation") {
            squareOauthManager.handleRevokeWebhook(requestBody, signature)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.badRequest().build()
    }

    @GetMapping("/oauth/revoke")
    fun revokeSquareOauth(): ResponseEntity<ApiResponse<LogOutResponse>> {
        val response = LogOutResponse(
            squareOauthManager.revokeSquareOauth()
        )
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/webhook/payment")
    fun handlePaymentWebhook(
        @RequestBody requestBody: PaymentWebhookRequest,
        @RequestHeader("x-square-hmacsha256-signature") signature: String
    ): ResponseEntity<String> {
        if (requestBody.data.objectX.payment.status == "COMPLETED") {
            squareOauthManager.handlePaymentWebhook(requestBody, signature)
        }
        return ResponseEntity.ok().build()
    }
}