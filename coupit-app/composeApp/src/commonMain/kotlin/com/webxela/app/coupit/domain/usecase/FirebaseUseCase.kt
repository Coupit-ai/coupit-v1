package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.DeviceType
import com.webxela.app.coupit.domain.model.FcmToken
import com.webxela.app.coupit.domain.repo.FirebaseRepo

class FirebaseUseCase(private val firebaseRepo: FirebaseRepo) {

    suspend fun updateNewToken(
        token: String,
        deviceType: DeviceType
    ): ApiResponse<FcmToken, DataError.Remote> {
        return firebaseRepo.updateNewToken(token, deviceType)
    }
}