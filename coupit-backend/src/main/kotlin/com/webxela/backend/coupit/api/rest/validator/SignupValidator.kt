package com.webxela.backend.coupit.api.rest.validator

import com.webxela.backend.coupit.api.rest.dto.auth.SignupRequest
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.utils.Constants
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component

@Component
class SignupValidator {

    companion object {
        private val logger = LogManager.getLogger(SignupValidator::class.java)
    }

    fun validate(signupRequest: SignupRequest) {

        if (signupRequest.email.matches(Constants.EMAIL_PATTERN.toRegex()).not()) {
            logger.error("Invalid email address format")
            throw ApiError.BadRequest("Invalid email address")
        }
        if (signupRequest.password.length < 8) {
            logger.error("Password must be at least 8 characters long")
            throw ApiError.BadRequest("Password must be at least 8 characters long")
        }
        if (signupRequest.firstName.isBlank()) {
            logger.error("First name cannot be empty")
            throw ApiError.BadRequest("First name cannot be empty")
        }
    }
}