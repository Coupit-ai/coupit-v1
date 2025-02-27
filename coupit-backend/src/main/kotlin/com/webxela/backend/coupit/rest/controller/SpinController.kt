package com.webxela.backend.coupit.rest.controller


import com.webxela.backend.coupit.rest.dto.SpinConfigResponse
import com.webxela.backend.coupit.rest.dto.SpinRequest
import com.webxela.backend.coupit.rest.dto.SpinResponse
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.service.SpinService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/v1/spin")
class SpinController(private val spinService: SpinService) {

    @PostMapping
    fun performSpin(
        @RequestBody requestBody: SpinRequest
    ): ResponseEntity<ApiResponse<SpinResponse>> {
        val spinResponse = spinService.performSpin(
            sessionId = requestBody.sessionId
        )
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

    @GetMapping("/config/{sessionId}")
    fun getSpinConfig(
        @PathVariable sessionId: UUID
    ): ResponseEntity<ApiResponse<SpinConfigResponse>> {
        val spinConfig = spinService.getSpinConfig(sessionId)
        return ResponseEntity.ok(ApiResponse.success(spinConfig))
    }

    @GetMapping("/{spinId}")
    fun getSpinResult(
        @PathVariable spinId: UUID
    ): ResponseEntity<ApiResponse<SpinResponse>> {
        val spinResponse = spinService.getOrRedeemReward(spinId, false)
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }
}