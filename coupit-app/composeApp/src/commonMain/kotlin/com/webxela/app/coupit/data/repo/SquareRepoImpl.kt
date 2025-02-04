package com.webxela.app.coupit.data.repo

import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.map
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.data.local.DataStoreManager
import com.webxela.app.coupit.data.model.dto.JwtPayload
import com.webxela.app.coupit.data.model.mapper.ConnectionDtoMapper.toConnection
import com.webxela.app.coupit.data.remote.SquareManager
import com.webxela.app.coupit.domain.model.Connection
import com.webxela.app.coupit.domain.repo.SquareRepo
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import saschpe.kase64.base64UrlDecoded

class SquareRepoImpl(
    private val squareManager: SquareManager,
    private val dataStoreManager: DataStoreManager
) : SquareRepo {

    override suspend fun connectWithSquare(state: String): ApiResponse<Connection, DataError.Remote> {
        return squareManager.connectWithSquare(state).map { it.toConnection() }
    }

    override suspend fun revokeSquareConnection() {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfJwtExpired(token: String): Boolean {
        try {
            val payload = token.split(".")[1]
            val decodedPayload = payload.base64UrlDecoded
            val expiry = Json.decodeFromString<JwtPayload>(decodedPayload).exp ?: return true
            return expiry < (Clock.System.now().toEpochMilliseconds() / 1000)
        } catch (ex: Exception) {
            Logger.e("Error while checking jwt token expiry", ex)
            return true
        }
    }

    override fun getJwtToken(): String? {
        return dataStoreManager.getStringFromVault(AppConstant.SECURE_JWT_TOKEN)
    }

}