package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.DeviceType
import com.webxela.app.coupit.domain.model.FcmToken

interface FirebaseRepo {

    suspend fun updateNewToken(
        token: String,
        deviceType: DeviceType
    ): ApiResponse<FcmToken, DataError.Remote>

}