package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.dto.RedirectResponse
import com.webxela.backend.coupit.application.service.SquareOauthManager
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
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
            redirectUri = squareOauthManager.buildSquareAuthUrl(),
            message = "Follow this url to start oauth flow"
        )
        return ResponseEntity.ok(ApiResponse.success(loginResponse))
    }

    @GetMapping("/oauth/callback")
    fun handleLoginCallback(
        @RequestParam("code", required = false) code: String?,
        @RequestParam("error", required = false) error: String?,
        @RequestParam("state") state: String
    ): ResponseEntity<String> {

        var redirectUri = "coupit://callback?"
        redirectUri += if (error != null || code == null) {
            "error=${URLEncoder.encode(error ?: "Authorization failed", "UTF-8")}"
        } else {
            try {
                val jwtToken = squareOauthManager.processSquareOauthCallback(code, state)
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


    @GetMapping("/webhook/payment")
    fun handlePaymentWebhook() {

    }

    @GetMapping("/webhook/revoke")
    fun handleLogoutWebhook() {

    }

}