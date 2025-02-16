package com.webxela.app.coupit.data.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllRewardsDto(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("message")
    val message: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("timeStamp")
    val timeStamp: String
) {
    @Serializable
    data class Data(
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
}