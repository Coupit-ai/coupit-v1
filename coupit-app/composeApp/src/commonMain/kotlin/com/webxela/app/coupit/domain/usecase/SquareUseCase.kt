package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.Connection
import com.webxela.app.coupit.domain.repo.SquareRepo

class SquareUseCase(private val squareRepo: SquareRepo) {

    suspend fun connectWithSquare(state: String): ApiResponse<Connection, DataError.Remote> {
        return squareRepo.connectWithSquare(state)
    }

    suspend fun checkIfJwtExpired(token: String): Boolean {
        return squareRepo.checkIfJwtExpired(token)
    }

    fun getJwtToken(): String? {
        return squareRepo.getJwtToken()
    }
}