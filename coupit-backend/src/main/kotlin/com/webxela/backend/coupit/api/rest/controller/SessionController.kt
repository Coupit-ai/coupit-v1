package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.model.dto.SessionRequest
import com.webxela.backend.coupit.api.rest.model.dto.SessionResponse
import com.webxela.backend.coupit.application.service.SessionManagementService
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/session")
class SessionController(
    private val sessionManagementService: SessionManagementService
) {

    @PostMapping("/create")
    fun createSession(
        @RequestBody sessionReq: SessionRequest
    ): ResponseEntity<ApiResponse<SessionResponse>> {

        val session = sessionManagementService.createSession(
            merchantId = sessionReq.merchantId,
            transactionId = sessionReq.transactionId
        )
        return ResponseEntity.ok(ApiResponse.success(session))
    }


}