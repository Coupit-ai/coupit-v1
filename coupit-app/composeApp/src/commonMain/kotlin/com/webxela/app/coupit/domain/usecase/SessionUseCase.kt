package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.Session
import com.webxela.app.coupit.domain.repo.SessionRepo

class SessionUseCase(
    private val sessionRepo: SessionRepo
) {

    suspend fun createSession(
        merchantId: String,
        transactionId: String
    ): ApiResponse<Session, DataError.Remote> {
        return sessionRepo.createSession(merchantId, transactionId)
    }

    suspend fun getSession(sessionId: String): ApiResponse<Session, DataError.Remote> {
        return sessionRepo.getSession(sessionId)
    }
}