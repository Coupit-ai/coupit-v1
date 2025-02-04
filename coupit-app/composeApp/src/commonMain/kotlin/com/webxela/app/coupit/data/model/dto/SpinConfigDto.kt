package com.webxela.app.coupit.data.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpinConfigDto(
    @SerialName("data")
    val `data`: Data,
    @SerialName("message")
    val message: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("timeStamp")
    val timeStamp: String
) {
    @Serializable
    data class Data(
        @SerialName("rewards")
        val rewards: List<Reward>,
        @SerialName("session")
        val session: Session
    ) {
        @Serializable
        data class Reward(
            @SerialName("description")
            val description: String,
            @SerialName("id")
            val id: String,
            @SerialName("probability")
            val probability: Double,
            @SerialName("title")
            val title: String,
            @SerialName("validityHours")
            val validityHours: Int
        )

        @Serializable
        data class Session(
            @SerialName("createdAt")
            val createdAt: String,
            @SerialName("expiresAt")
            val expiresAt: String,
            @SerialName("id")
            val id: String,
            @SerialName("used")
            val used: Boolean
        )
    }
}