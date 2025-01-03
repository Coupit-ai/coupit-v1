package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.Session
import java.util.UUID

interface SessionRepo {

    fun createSession(merchantId: String, transactionId: String): Session
    fun getSessionByTransactionId(transactionId: String): Session?
    fun markSessionAsUsed(sessionId: UUID): Boolean
    fun getSessionBySessionId(sessionId: UUID): Session?
    fun deleteSession(transactionId: String): Boolean

}