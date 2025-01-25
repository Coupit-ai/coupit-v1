package com.webxela.backend.coupit.api.controller

import com.webxela.backend.coupit.api.dto.RewardRequest
import com.webxela.backend.coupit.api.dto.RewardResponse
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.service.RewardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/reward")
class RewardController(private val rewardService: RewardService) {

    @PostMapping
    fun createNewReward(
        @RequestBody rewardRequest: RewardRequest
    ): ResponseEntity<ApiResponse<RewardResponse>> {

        val reward = rewardService.createNewReward(rewardRequest)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(reward))
    }
}