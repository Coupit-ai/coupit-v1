package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.dto.SessionRequest
import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.application.service.SessionManager
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/api/v1")
class SessionController(
    private val sessionManager: SessionManager
) {

    @PostMapping("/session")
    fun createSession(
        @RequestBody sessionReq: SessionRequest
    ): ResponseEntity<ApiResponse<SessionResponse>> {

        val session = sessionManager.createSession(
            merchantId = sessionReq.merchantId,
            transactionId = sessionReq.transactionId
        )
        return ResponseEntity.ok(ApiResponse.success(session))
    }

    @GetMapping("/session/{sessionId}")
    fun getSession(
        @PathVariable sessionId: UUID
    ): ResponseEntity<ApiResponse<SessionResponse>> {

        val session = sessionManager.getSession(sessionId)
        return ResponseEntity.ok(ApiResponse.success(session))
    }

}