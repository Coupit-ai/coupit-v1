package com.webxela.backend.coupit.api.controller

import com.webxela.backend.coupit.api.dto.FcmTokenRequest
import com.webxela.backend.coupit.api.dto.FcmTokenResponse
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.service.FcmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/fcm")
class FcmController(private val fcmService: FcmService) {

    @PostMapping("/token")
    fun registerToken(
        @RequestBody requestBody: FcmTokenRequest
    ): ResponseEntity<ApiResponse<FcmTokenResponse>> {
        val token = fcmService.registerFcmToken(requestBody)
        val response = FcmTokenResponse(
            token = token,
            message = "Fcm token registered!"
        )
        return ResponseEntity.ok(ApiResponse.success(response))
    }

}