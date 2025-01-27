package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.map
import com.webxela.app.coupit.data.model.mapper.SpinResultDtoMapper.toSpinResult
import com.webxela.app.coupit.data.remote.SpinManager
import com.webxela.app.coupit.domain.model.SpinResult
import com.webxela.app.coupit.domain.repo.SpinRepo

class SpinRepoImpl(
    private val spinManager: SpinManager
) : SpinRepo {

    override suspend fun performSpin(
        merchantId: String,
        sessionId: String
    ): ApiResponse<SpinResult, DataError.Remote> {

        return spinManager.performSpin(merchantId, sessionId).map { it.toSpinResult() }
    }

    override suspend fun getSpinResult(spinId: String): ApiResponse<SpinResult, DataError.Remote> {

        return spinManager.getSpinResult(spinId).map { it.toSpinResult() }
    }
}