package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.model.dto.OfferResponse
import com.webxela.backend.coupit.api.rest.model.mapper.OfferRespMapper.toOfferResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.domain.usecase.OfferUseCase
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service


@Service
class OfferManagementService(
    private val offerUseCase: OfferUseCase
) {

    private val logger = LogManager.getLogger(OfferManagementService::class.java)

    fun getAllOffers(merchantId: String): List<OfferResponse> {
        try {

            return offerUseCase.getAllOffers(merchantId).map { it.toOfferResponse() }

        } catch (ex: Exception) {
            logger.error("Error while fetching offers", ex)
            throw ApiError.InternalError(ex, message = "Failed to fetch offers.")
        }
    }
}