package com.webxela.backend.coupit.api.rest.model.mapper

import com.webxela.backend.coupit.api.rest.dto.OfferResponse
import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.domain.model.Session

object SessionRespMapper {

    fun Session.toSessionResponse(offers: List<OfferResponse> = emptyList()): SessionResponse {
        return SessionResponse(
            id = this.id,
            merchantId = this.merchantId,
            transactionId = this.transactionId,
            used = this.used,
            sessionId = this.sessionId,
            expiresAt = this.expiresAt,
            offers = offers
        )
    }

}