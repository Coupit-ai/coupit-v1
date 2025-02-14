package com.webxela.backend.coupit.rest.controller

import com.webxela.backend.coupit.rest.dto.RewardRequest
import com.webxela.backend.coupit.rest.dto.RewardResponse
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.rest.dto.SpinResponse
import com.webxela.backend.coupit.service.RewardService
import com.webxela.backend.coupit.service.SpinService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/reward")
class RewardController(
    private val rewardService: RewardService,
    private val spinService: SpinService
) {

    @PostMapping
    fun createNewReward(
        @RequestBody requestBody: List<RewardRequest>
    ): ResponseEntity<ApiResponse<List<RewardResponse>>> {
        val reward = rewardService.createNewRewards(requestBody)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(reward))
    }

    @GetMapping("/redeem/{spinId}")
    fun redeemReward(
        @PathVariable spinId: UUID
    ): ResponseEntity<ApiResponse<SpinResponse>> {
        val spinResponse = spinService.getOrRedeemReward(spinId, true)
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

}