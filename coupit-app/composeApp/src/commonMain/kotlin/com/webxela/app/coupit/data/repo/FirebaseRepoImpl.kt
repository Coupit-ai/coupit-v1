package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.DeviceType
import com.webxela.app.coupit.core.domain.map
import com.webxela.app.coupit.data.model.mapper.FcmTokenDtoMapper.toFcmToken
import com.webxela.app.coupit.data.remote.FirebaseManager
import com.webxela.app.coupit.domain.model.FcmToken
import com.webxela.app.coupit.domain.repo.FirebaseRepo

class FirebaseRepoImpl(private val firebaseManager: FirebaseManager) : FirebaseRepo {

    override suspend fun updateNewToken(
        token: String,
        deviceType: DeviceType,
    ): ApiResponse<FcmToken, DataError.Remote> {
        return firebaseManager.updateNewToken(token, deviceType).map { it.toFcmToken() }
    }

}