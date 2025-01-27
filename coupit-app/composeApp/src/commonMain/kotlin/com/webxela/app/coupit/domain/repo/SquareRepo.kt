package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.Connection

interface SquareRepo {

    suspend fun connectWithSquare(state: String): ApiResponse<Connection, DataError.Remote>

    suspend fun revokeSquareConnection()
}