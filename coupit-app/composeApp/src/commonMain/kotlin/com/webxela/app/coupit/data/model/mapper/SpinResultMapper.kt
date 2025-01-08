package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.SpinResultDto
import com.webxela.app.coupit.domain.model.SpinResult

object SpinResultMapper {

    fun SpinResultDto.toSpinResult(): SpinResult {
        return SpinResult(
            message = this.message,
            statusCode = this.statusCode,
            timeStamp = this.timeStamp,
            data = this.data.toSpinData()
        )
    }

    private fun SpinResultDto.Data.toSpinData(): SpinResult.Data {
        return SpinResult.Data(
            expiresAt = this.expiresAt,
            merchantId = this.merchantId,
            sessionId = this.sessionId,
            timeStamp = this.timeStamp,
            claimed = this.claimed,
            qrCode = this.qrCode,
            spinId = this.spinId,
            offer = this.offer.toSpinOffer()
        )
    }

    private fun SpinResultDto.Data.Offer.toSpinOffer(): SpinResult.Data.Offer {
        return SpinResult.Data.Offer(
            description = this.description,
            offerId = this.offerId,
            timeStamp = this.timeStamp,
            title = this.title
        )
    }
}