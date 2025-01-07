package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.domain.model.Session
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SessionRepo {

    fun createSession(merchantId: String, transactionId: String): Session
    @OptIn(ExperimentalUuidApi::class)
    fun getSession(sessionId: Uuid): Session

}