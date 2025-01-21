package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.SpinSession

interface SessionRepository {

    fun createSession(merchantId: String, paymentId: String): SpinSession
    fun getSession(paymentId: String, merchantId: String): SpinSession?
    fun markSessionAsUsed(sessionId: String): Boolean
    fun getSessionBySessionId(sessionId: String): SpinSession?
    fun deleteSession(paymentId: String, merchantId: String): Boolean

}