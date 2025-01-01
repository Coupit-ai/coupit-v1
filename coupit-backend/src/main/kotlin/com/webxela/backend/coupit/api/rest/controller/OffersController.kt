package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.model.dto.OfferResponse
import com.webxela.backend.coupit.application.service.OfferManagementService
import com.webxela.backend.coupit.common.exception.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/offers")
class OffersController(
    private val offerManagementService: OfferManagementService
) {

    @GetMapping("{merchantId}/retrieve")
    fun getAllOffers(
        @PathVariable merchantId: String
    ): ResponseEntity<ApiResponse<List<OfferResponse>>> {

        val offers = offerManagementService.getAllOffers(merchantId)

        return ResponseEntity.ok(ApiResponse.success(offers))
    }

}