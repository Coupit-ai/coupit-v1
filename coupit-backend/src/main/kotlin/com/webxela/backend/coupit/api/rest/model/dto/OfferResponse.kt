package com.webxela.backend.coupit.api.rest.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OfferResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String
)
