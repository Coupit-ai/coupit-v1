package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.SpinConfig
import com.webxela.app.coupit.domain.model.SpinResult
import com.webxela.app.coupit.domain.repo.SpinRepo

class SpinUseCase(private val spinRepo: SpinRepo) {

    suspend fun performSpin(sessionId: String): ApiResponse<SpinResult, DataError.Remote> {
        return spinRepo.performSpin(sessionId)
    }

    suspend fun getSpinConfig(sessionId: String):  ApiResponse<SpinConfig, DataError.Remote> {
        return spinRepo.getSpinConfig(sessionId)
    }

    suspend fun getSpinResult(spinId: String): ApiResponse<SpinResult, DataError.Remote> {
        return spinRepo.getSpinResult(spinId)
    }

}