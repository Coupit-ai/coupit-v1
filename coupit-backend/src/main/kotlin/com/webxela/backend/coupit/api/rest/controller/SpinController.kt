package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.dto.SpinRequest
import com.webxela.backend.coupit.api.rest.dto.SpinResponse
import com.webxela.backend.coupit.application.service.SpinManager
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/spin")
class SpinController(private val spinManager: SpinManager) {

    @PostMapping
    fun performSpin(
        @RequestBody spinRequest: SpinRequest
    ): ResponseEntity<ApiResponse<SpinResponse>> {

        val spinResponse = spinManager.performSpin(
            merchantId = spinRequest.merchantId,
            sessionId = spinRequest.sessionId
        )
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

    @GetMapping("{spinId}")
    fun getSpinResult(
        @PathVariable spinId: String
    ): ResponseEntity<ApiResponse<SpinResponse>> {

        val spinResponse = spinManager.getSpinResult(spinId)
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

}