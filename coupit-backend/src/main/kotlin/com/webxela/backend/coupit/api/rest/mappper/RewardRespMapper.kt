package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.RewardResponse
import com.webxela.backend.coupit.domain.model.Reward

object RewardRespMapper {

    fun Reward.toOfferResponse(): RewardResponse {
        return RewardResponse(
            id = this.rewardId,
            timeStamp = this.timeStamp,
            description = this.description,
            title = this.title
        )
    }

}