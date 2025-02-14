package com.webxela.backend.coupit.rest.dto.auth


import com.fasterxml.jackson.annotation.JsonProperty

data class RevokeWebhookRequest(
    @JsonProperty("created_at")
    val createdAt: String?,
    @JsonProperty("data")
    val data: com.webxela.backend.coupit.rest.dto.auth.RevokeWebhookRequest.Data?,
    @JsonProperty("event_id")
    val eventId: String?,
    @JsonProperty("merchant_id")
    val merchantId: String,
    @JsonProperty("type")
    val type: String
) {
    data class Data(
        @JsonProperty("id")
        val id: String?,
        @JsonProperty("object")
        val objectX: com.webxela.backend.coupit.rest.dto.auth.RevokeWebhookRequest.Data.Object?,
        @JsonProperty("type")
        val type: String?
    ) {
        data class Object(
            @JsonProperty("revocation")
            val revocation: com.webxela.backend.coupit.rest.dto.auth.RevokeWebhookRequest.Data.Object.Revocation?
        ) {
            data class Revocation(
                @JsonProperty("revoked_at")
                val revokedAt: String?,
                @JsonProperty("revoker_type")
                val revokerType: String?
            )
        }
    }
}