package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.OfferResponse
import com.webxela.backend.coupit.api.rest.dto.SessionResponse
import com.webxela.backend.coupit.domain.model.Session

object SessionRespMapper {

    fun Session.toSessionResponse(offers: List<OfferResponse>): SessionResponse {
        return SessionResponse(
            merchantId = this.merchantId,
            transactionId = this.transactionId,
            timeStamp = this.timeStamp,
            used = this.used,
            sessionId = this.sessionId,
            expiresAt = this.expiresAt,
            offers = offers
        )
    }

}