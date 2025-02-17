package com.webxela.backend.coupit.rest.validator

import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.rest.dto.auth.SignupRequest
import com.webxela.backend.coupit.utils.AppConstants
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component

@Component
class SignupValidator {

    companion object {
        private val logger = LogManager.getLogger(SignupValidator::class.java)
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 50
        private const val MAX_NAME_LENGTH = 50
        private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()
        private val NAME_PATTERN = "^[a-zA-Z\\s-']+$".toRegex()
    }

    fun validate(signupRequest: SignupRequest) {
        validateEmail(signupRequest.email)
        validatePassword(signupRequest.password)
        validateName(signupRequest.firstName, "First name")
        signupRequest.lastName?.let { validateName(it, "Last name") }
    }

    private fun validateEmail(email: String) {
        if (email.isBlank()) {
            logger.error("Email cannot be empty")
            throw ApiError.BadRequest("Email cannot be empty")
        }
        if (!email.matches(AppConstants.EMAIL_PATTERN.toRegex())) {
            logger.error("Invalid email address format: $email")
            throw ApiError.BadRequest("Invalid email address format")
        }
    }

    private fun validatePassword(password: String) {
        when {
            password.isBlank() -> {
                logger.error("Password cannot be empty")
                throw ApiError.BadRequest("Password cannot be empty")
            }
            password.length < MIN_PASSWORD_LENGTH -> {
                logger.error("Password must be at least $MIN_PASSWORD_LENGTH characters long")
                throw ApiError.BadRequest("Password must be at least $MIN_PASSWORD_LENGTH characters long")
            }
            password.length > MAX_PASSWORD_LENGTH -> {
                logger.error("Password cannot exceed $MAX_PASSWORD_LENGTH characters")
                throw ApiError.BadRequest("Password cannot exceed $MAX_PASSWORD_LENGTH characters")
            }
            !password.matches(PASSWORD_PATTERN) -> {
                logger.error("Password must contain at least one digit, one lowercase, one uppercase letter, one special character, and no whitespace")
                throw ApiError.BadRequest("Password must contain at least one digit, one lowercase, one uppercase letter, one special character, and no whitespace")
            }
        }
    }

    private fun validateName(name: String, fieldName: String) {
        when {
            name.isBlank() -> {
                logger.error("$fieldName cannot be empty")
                throw ApiError.BadRequest("$fieldName cannot be empty")
            }
            name.length > MAX_NAME_LENGTH -> {
                logger.error("$fieldName cannot exceed $MAX_NAME_LENGTH characters")
                throw ApiError.BadRequest("$fieldName cannot exceed $MAX_NAME_LENGTH characters")
            }
            !name.matches(NAME_PATTERN) -> {
                logger.error("$fieldName contains invalid characters")
                throw ApiError.BadRequest("$fieldName can only contain letters, spaces, hyphens, and apostrophes")
            }
        }
    }
}