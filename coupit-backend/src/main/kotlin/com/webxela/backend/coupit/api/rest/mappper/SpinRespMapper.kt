package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.SpinResponse
import com.webxela.backend.coupit.api.rest.mappper.OfferRespMapper.toOfferResponse
import com.webxela.backend.coupit.domain.model.SpinResult

object SpinRespMapper {

    fun SpinResult.toSpinResponse(): SpinResponse {
        return SpinResponse(
            spinId = this.spinId,
            merchantId = this.merchantId,
            sessionId = this.sessionId,
            timeStamp = this.timeStamp,
            offer = this.offer.toOfferResponse(),
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed
        )
    }

}