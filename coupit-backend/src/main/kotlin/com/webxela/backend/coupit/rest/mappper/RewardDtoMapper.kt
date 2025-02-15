package com.webxela.backend.coupit.rest.mappper

import com.webxela.backend.coupit.rest.dto.RewardRequest
import com.webxela.backend.coupit.rest.dto.RewardResponse
import com.webxela.backend.coupit.rest.dto.SpinConfigResponse
import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.domain.model.SquareMerchant

object RewardDtoMapper {

    fun Reward.toRewardResponse(): RewardResponse {
        return RewardResponse(
            id = this.id!!,
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours
        )
    }

    fun SpinSession.toSpinConfigSession(): SpinConfigResponse.Session {
        return SpinConfigResponse.Session(
            id = this.id!!,
            createdAt = this.createdAt!!,
            expiresAt = this.expiresAt!!,
            used = this.used!!
        )
    }

    fun RewardRequest.toReward(merchant: SquareMerchant): Reward {
        return Reward(
            id = this.id,
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours,
            merchant = merchant
        )
    }

}