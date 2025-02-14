package com.webxela.backend.coupit.rest.validator

import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.utils.AppConstants
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component

@Component
class SignupValidator {

    companion object {
        private val logger = LogManager.getLogger(com.webxela.backend.coupit.rest.validator.SignupValidator::class.java)
    }

    fun validate(signupRequest: com.webxela.backend.coupit.rest.dto.auth.SignupRequest) {

        if (signupRequest.email.matches(AppConstants.EMAIL_PATTERN.toRegex()).not()) {
            com.webxela.backend.coupit.rest.validator.SignupValidator.Companion.logger.error("Invalid email address format")
            throw ApiError.BadRequest("Invalid email address")
        }
        if (signupRequest.password.length < 8) {
            com.webxela.backend.coupit.rest.validator.SignupValidator.Companion.logger.error("Password must be at least 8 characters long")
            throw ApiError.BadRequest("Password must be at least 8 characters long")
        }
        if (signupRequest.firstName.isBlank()) {
            com.webxela.backend.coupit.rest.validator.SignupValidator.Companion.logger.error("First name cannot be empty")
            throw ApiError.BadRequest("First name cannot be empty")
        }
    }
}