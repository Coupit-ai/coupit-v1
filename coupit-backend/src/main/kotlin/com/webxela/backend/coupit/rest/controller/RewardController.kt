package com.webxela.backend.coupit.rest.controller

import com.webxela.backend.coupit.rest.dto.RewardRequest
import com.webxela.backend.coupit.rest.dto.RewardResponse
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.rest.dto.RewardDeleteResponse
import com.webxela.backend.coupit.rest.dto.SpinResponse
import com.webxela.backend.coupit.rest.validator.RewardValidator
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
    private val spinService: SpinService,
    private val rewardValidator: RewardValidator
) {

    @PostMapping
    fun createReward(
        @RequestBody requestBody: RewardRequest
    ): ResponseEntity<ApiResponse<RewardResponse>> {
        rewardValidator.validate(requestBody)
        val reward = rewardService.createReward(requestBody)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(reward))
    }

    @GetMapping
    fun getAllRewards(): ResponseEntity<ApiResponse<List<RewardResponse>>> {
        val rewards = rewardService.getAllRewards()
        return ResponseEntity.ok(ApiResponse.success(rewards))
    }

    @GetMapping("/redeem/{spinId}")
    fun redeemReward(
        @PathVariable spinId: UUID
    ): ResponseEntity<ApiResponse<SpinResponse>> {
        val spinResponse = spinService.getOrRedeemReward(spinId, true)
        return ResponseEntity.ok(ApiResponse.success(spinResponse))
    }

    @DeleteMapping("/{rewardId}")
    fun deleteReward(
        @PathVariable rewardId: UUID
    ): ResponseEntity<ApiResponse<RewardDeleteResponse>> {
        rewardService.deleteReward(rewardId)
        val response = RewardDeleteResponse("Reward $rewardId deleted successfully.")
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PutMapping
    fun updateReward(
        @RequestBody requestBody: RewardRequest
    ): ResponseEntity<ApiResponse<RewardResponse>> {
        rewardValidator.validate(requestBody)
        val reward = rewardService.updateReward(requestBody)
        return ResponseEntity.ok(ApiResponse.success(reward))
    }

}