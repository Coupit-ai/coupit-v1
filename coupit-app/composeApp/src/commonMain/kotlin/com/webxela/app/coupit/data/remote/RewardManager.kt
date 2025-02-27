package com.webxela.app.coupit.data.remote

import com.webxela.app.coupit.core.data.safeCall
import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.data.model.dto.AllRewardsDto
import com.webxela.app.coupit.data.model.dto.DeleteRewardDto
import com.webxela.app.coupit.data.model.dto.RewardDto
import com.webxela.app.coupit.data.model.dto.RewardRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RewardManager(private val httpClient: HttpClient) {

    suspend fun getAllRewards(): ApiResponse<AllRewardsDto, DataError.Remote> {
        return safeCall<AllRewardsDto> {
            httpClient.get("${AppConstant.BASE_URL}/reward") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun createReward(rewardRequest: RewardRequest): ApiResponse<RewardDto, DataError.Remote> {
        return safeCall<RewardDto> {
            httpClient.post("${AppConstant.BASE_URL}/reward") {
                contentType(ContentType.Application.Json)
                setBody(rewardRequest)
            }
        }
    }

    suspend fun deleteReward(rewardId: String): ApiResponse<DeleteRewardDto, DataError.Remote> {
        return safeCall<DeleteRewardDto> {
            httpClient.delete("${AppConstant.BASE_URL}/reward/$rewardId") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun updateReward(rewardId: String, rewardRequest: RewardRequest): ApiResponse<RewardDto, DataError.Remote> {
        return safeCall<RewardDto> {
            httpClient.put("${AppConstant.BASE_URL}/reward/$rewardId") {
                contentType(ContentType.Application.Json)
                setBody(rewardRequest)
            }
        }
    }

}