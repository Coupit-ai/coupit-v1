package com.webxela.backend.coupit.api.controller


import com.webxela.backend.coupit.api.dto.SpinConfigResponse
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.service.SpinService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/spin")
class SpinController(private val spinService: SpinService) {

//    @PostMapping
//    fun performSpin(
//        @RequestBody spinRequest: SpinRequest
//    ): ResponseEntity<ApiResponse<SpinResponse>> {
//
//        val spinResponse = spinService.performSpin(
//            merchantId = spinRequest.merchantId,
//            sessionId = spinRequest.sessionId
//        )
//        return ResponseEntity.ok(ApiResponse.success(spinResponse))
//    }

//    @GetMapping("{spinId}")
//    fun getSpinResult(
//        @PathVariable spinId: String
//    ): ResponseEntity<ApiResponse<SpinResponse>> {
//
//        val spinResponse = spinService.getSpinResult(spinId)
//        return ResponseEntity.ok(ApiResponse.success(spinResponse))
//    }

    @GetMapping("/config/{sessionId}")
    fun getSpinConfig(
        @PathVariable sessionId: UUID
    ): ResponseEntity<ApiResponse<SpinConfigResponse>> {

        val spinConfig = spinService.getSpinConfig(sessionId)
        return ResponseEntity.ok(ApiResponse.success(spinConfig))
    }
}