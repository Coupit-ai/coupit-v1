package com.webxela.backend.coupit.rest.controller

import com.webxela.backend.coupit.rest.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.rest.dto.auth.LogOutResponse
import com.webxela.backend.coupit.rest.dto.auth.MerchantResponse
import com.webxela.backend.coupit.rest.dto.auth.RedirectResponse
import com.webxela.backend.coupit.rest.dto.auth.RevokeWebhookRequest
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.service.SquareOauthService
import com.webxela.backend.coupit.service.SquarePaymentService
import com.webxela.backend.coupit.service.UtilityService
import com.webxela.backend.coupit.rest.static.getOauthRedirectPage
import org.apache.logging.log4j.LogManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/square")
class SquareController(
    private val squareOauthService: SquareOauthService,
    private val squarePaymentService: SquarePaymentService,
    private val utilityService: UtilityService
) {

    companion object {
        private val logger = LogManager.getLogger(SquareController::class.java)
    }

    @GetMapping("/oauth/connect/{state}")
    fun loginToSquare(
        @PathVariable state: String,
    ): ResponseEntity<ApiResponse<RedirectResponse>> {
        val loginResponse = RedirectResponse(
            redirectUri = squareOauthService.buildSquareOauthUrl(state),
            message = "Follow this url to start oauth flow"
        )
        return ResponseEntity.ok(ApiResponse.success(loginResponse))
    }

    @GetMapping("/oauth/callback")
    fun handleLoginCallback(
        @RequestParam("code", required = false) code: String?,
        @RequestParam("error", required = false) error: String?,
        @RequestParam("state", required = false) state: String?,
    ): ResponseEntity<String> {
        val redirectUri = if (error != null || code == null || state == null) {
            utilityService.buildErrorUri(error ?: "Authorization failed")
        } else {
            try {
                val jwtToken = squareOauthService.processSquareOauthCallback(code, state)
                jwtToken?.let {
                    utilityService.buildSuccessUri(jwtToken, state)
                } ?: utilityService.buildErrorUri("Token generation failed")
            } catch (ex: Exception) {
                logger.error("Token generation failed: ${ex.message}", ex)
                utilityService.buildErrorUri("Token generation failed: ${ex.message}")
            }
        }
        logger.info("Redirecting OAuth flow to $redirectUri")
        val html = getOauthRedirectPage(redirectUri)
        return ResponseEntity.ok()
            .header("Content-Type", "text/html")
            .body(html)
    }

    @PostMapping("/webhook/revoke")
    fun handleLogoutWebhook(
        @RequestBody requestBody: RevokeWebhookRequest,
//        @RequestHeader("x-square-hmacsha256-signature") signature: String
    ): ResponseEntity<Nothing> {
        val signature = "" // TODO: remove this line
        if (requestBody.data?.type == "revocation") {
            squareOauthService.handleRevokeWebhook(requestBody, signature)
        }
        return ResponseEntity.ok().build() // Return OK whatever happens
    }

    @GetMapping("/oauth/revoke")
    fun revokeSquareOauth(): ResponseEntity<ApiResponse<LogOutResponse>> {
        val response = LogOutResponse(
            squareOauthService.revokeSquareOauth()
        )
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PostMapping("/webhook/payment")
    fun handlePaymentWebhook(
        @RequestBody requestBody: PaymentWebhookRequest,
//        @RequestHeader("x-square-hmacsha256-signature") signature: String
    ): ResponseEntity<String> {
        val signature = "" // TODO: remove this line
        if (requestBody.data.objectX.payment.status == "COMPLETED") {
            val response = squarePaymentService.handlePaymentWebhook(requestBody, signature)
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.ok("Something went wrong") // Return OK whatever happens
    }

    @GetMapping("/me")
    fun getLoggedInUser(): ResponseEntity<ApiResponse<MerchantResponse>> {
        val response = squareOauthService.getLoggedInMerchant()
        return ResponseEntity.ok(ApiResponse.success(response))
    }
}