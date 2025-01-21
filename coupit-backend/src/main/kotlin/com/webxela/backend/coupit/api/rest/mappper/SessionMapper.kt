package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.RewardResponse
import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.domain.model.Session

object SessionMapper {

    fun Session.toSessionResponse(offers: List<RewardResponse>): SessionResponse {
        return SessionResponse(
            id = this.sessionId,
            merchantId = this.merchantId,
            transactionId = this.transactionId,
            timeStamp = this.timeStamp,
            used = this.used,
            expiresAt = this.expiresAt,
            offers = offers
        )
    }

}