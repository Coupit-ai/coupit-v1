package com.webxela.app.coupit.data.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionDto(
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
        @SerialName("message")
        val message: String,
        @SerialName("redirectUri")
        val redirectUri: String
    )
}