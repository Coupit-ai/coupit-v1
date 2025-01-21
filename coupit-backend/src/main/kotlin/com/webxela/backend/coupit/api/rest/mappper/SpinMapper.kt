package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.SpinResponse
import com.webxela.backend.coupit.api.rest.mappper.RewardMapper.toOfferResponse
import com.webxela.backend.coupit.domain.model.SpinResult

object SpinMapper {

    fun SpinResult.toSpinResponse(): SpinResponse {
        return SpinResponse(
            id = this.spinId,
            merchantId = this.merchantId,
            sessionId = this.sessionId,
            timeStamp = this.timeStamp,
            offer = this.reward.toOfferResponse(),
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed
        )
    }

}