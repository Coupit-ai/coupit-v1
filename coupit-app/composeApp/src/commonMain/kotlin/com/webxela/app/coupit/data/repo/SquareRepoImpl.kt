package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.map
import com.webxela.app.coupit.data.model.mapper.ConnectionDtoMapper.toConnection
import com.webxela.app.coupit.data.remote.SquareManager
import com.webxela.app.coupit.domain.model.Connection
import com.webxela.app.coupit.domain.repo.SquareRepo

class SquareRepoImpl(
    private val squareManager: SquareManager
) : SquareRepo {

    override suspend fun connectWithSquare(state: String): ApiResponse<Connection, DataError.Remote> {
        return squareManager.connectWithSquare(state).map { it.toConnection() }
    }

    override suspend fun revokeSquareConnection() {
        TODO("Not yet implemented")
    }

}