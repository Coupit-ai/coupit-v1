package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.SpinResult
import com.webxela.app.coupit.domain.repo.SpinRepo

class SpinRepoImpl : SpinRepo {

    override fun performSpin(
        merchantId: String,
        sessionId: String
    ): ApiResponse<SpinResult, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override fun getSpinResult(spinId: String): ApiResponse<SpinResult, DataError.Remote> {
        TODO("Not yet implemented")
    }
}