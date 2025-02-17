package com.webxela.backend.coupit.rest.validator

import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.rest.dto.RewardRequest
import org.springframework.stereotype.Component

@Component
class RewardValidator {

    companion object {
        private const val MAX_TITLE_LENGTH = 100
        private const val MAX_DESCRIPTION_LENGTH = 500
        private const val MAX_VALIDITY_HOURS = 720
    }

    fun validate(rewardRequest: RewardRequest) {
        when {
            rewardRequest.title.isBlank() -> {
                throw ApiError.BadRequest("Title cannot be empty")
            }

            rewardRequest.title.length > MAX_TITLE_LENGTH -> {
                throw ApiError.BadRequest("Title cannot exceed $MAX_TITLE_LENGTH characters")
            }

            rewardRequest.description.isBlank() -> {
                throw ApiError.BadRequest("Description cannot be empty")
            }

            rewardRequest.description.length > MAX_DESCRIPTION_LENGTH -> {
                throw ApiError.BadRequest("Description cannot exceed $MAX_DESCRIPTION_LENGTH characters")
            }

            rewardRequest.probability < 0.0 -> {
                throw ApiError.BadRequest("Probability cannot be negative")
            }

            rewardRequest.probability > 1.0 -> {
                throw ApiError.BadRequest("Probability cannot be greater than 1")
            }

            rewardRequest.validityHours <= 0 -> {
                throw ApiError.BadRequest("Validity hours must be positive")
            }

            rewardRequest.validityHours > MAX_VALIDITY_HOURS -> {
                throw ApiError.BadRequest("Validity hours cannot exceed $MAX_VALIDITY_HOURS hours")
            }
        }
    }
}