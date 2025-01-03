package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.dto.SessionRequest
import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.api.rest.dto.SpinRequest
import com.webxela.backend.coupit.api.rest.dto.SpinResponse
import com.webxela.backend.coupit.application.service.SessionManager
import com.webxela.backend.coupit.application.service.SpinManager
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/api/v1")
class SpinController(
    private val sessionManager: SessionManager,
    private val spinManager: SpinManager
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

    @PostMapping("/spin")
    fun performSpin(
        @RequestBody spinRequest: SpinRequest
    ): ResponseEntity<ApiResponse<SpinResponse>> {

        val spinResponse =  spinManager.performSpin(
            merchantId = spinRequest.merchantId,
            sessionId = spinRequest.sessionId
        )
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

    @GetMapping("/spin/{spinId}")
    fun getSpinResult(
        @PathVariable spinId: UUID
    ): ResponseEntity<ApiResponse<SpinResponse>> {

        val spinResponse = spinManager.getSpinResult(spinId)
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

}