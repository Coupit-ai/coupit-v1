package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.map
import com.webxela.app.coupit.data.model.mapper.SessionMapper.toSession
import com.webxela.app.coupit.data.remote.SessionManager
import com.webxela.app.coupit.domain.model.Session
import com.webxela.app.coupit.domain.repo.SessionRepo


class SessionRepoImpl(
    private val sessionManager: SessionManager
) : SessionRepo {

    override suspend fun createSession(
        merchantId: String,
        transactionId: String
    ): ApiResponse<Session, DataError.Remote> {

        return sessionManager.createSession(merchantId, transactionId).map { it.toSession() }
    }

    override suspend fun getSession(
        sessionId: String
    ): ApiResponse<Session, DataError.Remote> {

        return sessionManager.getSession(sessionId).map { it.toSession() }
    }
}