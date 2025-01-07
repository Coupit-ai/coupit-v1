package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.Session

interface SessionRepo {

    suspend fun createSession(
        merchantId: String,
        transactionId: String
    ): ApiResponse<Session, DataError.Remote>

    suspend fun getSession(sessionId: String): ApiResponse<Session, DataError.Remote>

}