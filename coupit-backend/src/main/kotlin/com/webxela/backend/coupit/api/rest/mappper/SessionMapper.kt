package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.RewardResponse
import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.domain.model.SpinSession

object SessionMapper {

    fun SpinSession.toSessionResponse(offers: List<RewardResponse>): SessionResponse {
        return SessionResponse(
            id = this.sessionId,
            merchantId = this.merchantId,
            paymentId = this.paymentId,
            createdAt = this.createdAt,
            used = this.used,
            expiresAt = this.expiresAt,
            offers = offers
        )
    }

}