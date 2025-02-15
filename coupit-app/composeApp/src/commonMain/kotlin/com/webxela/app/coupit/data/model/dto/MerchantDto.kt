package com.webxela.app.coupit.data.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MerchantDto(
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
        @SerialName("businessName")
        val businessName: String,
        @SerialName("country")
        val country: String,
        @SerialName("createdAt")
        val createdAt: String,
        @SerialName("currency")
        val currency: String,
        @SerialName("id")
        val id: String,
        @SerialName("languageCode")
        val languageCode: String,
        @SerialName("mainLocationId")
        val mainLocationId: String,
        @SerialName("status")
        val status: String
    )
}