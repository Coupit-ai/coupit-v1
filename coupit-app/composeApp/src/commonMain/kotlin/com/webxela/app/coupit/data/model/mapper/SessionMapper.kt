package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.SessionDto
import com.webxela.app.coupit.domain.model.Session

object SessionMapper {

    fun SessionDto.toSession(): Session {
        return Session(
            message = this.message,
            statusCode = this.statusCode,
            timeStamp = this.timeStamp,
            error = this.error,
            data = this.data?.toSessionData()
        )
    }

    private fun SessionDto.Data.toSessionData(): Session.Data {
        return Session.Data(
            expiresAt = this.expiresAt,
            merchantId = this.merchantId,
            sessionId = this.sessionId,
            timeStamp = this.timeStamp,
            transactionId = this.transactionId,
            used = this.used,
            offers = this.offers.map { it.toSessionOffer() }
        )
    }

    private fun SessionDto.Data.Offer.toSessionOffer(): Session.Data.Offer {
        return Session.Data.Offer(
            description = this.description,
            offerId = this.offerId,
            timeStamp = this.timeStamp,
            title = this.title
        )
    }
}