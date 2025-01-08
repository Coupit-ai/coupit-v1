package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.SpinResult
import com.webxela.app.coupit.domain.repo.SpinRepo

class SpinUseCase(private val spinRepo: SpinRepo) {

    suspend fun performSpin(
        merchantId: String,
        sessionId: String
    ): ApiResponse<SpinResult, DataError.Remote> {

        return spinRepo.performSpin(merchantId, sessionId)
    }

    suspend fun getSpinResult(spinId: String): ApiResponse<SpinResult, DataError.Remote> {

        return spinRepo.getSpinResult(spinId)
    }

}