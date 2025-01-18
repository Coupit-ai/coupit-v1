package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.auth.LoginResponse
import com.webxela.backend.coupit.api.rest.dto.auth.SignupResponse
import com.webxela.backend.coupit.api.rest.mappper.AuthMapper.toSignupResponse
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.utils.Constants
import com.webxela.backend.coupit.common.utils.JwtUtils
import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.domain.usecase.UserUseCase
import org.apache.logging.log4j.LogManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserAuthManager(
    private val userUseCase: UserUseCase,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {

    companion object {
        private val logger = LogManager.getLogger(UserAuthManager::class.java)
    }

    fun registerNewUser(user: User): SignupResponse {

        logger.info("Registering new user with email: ${user.email}")
        if (user.email.matches(Constants.EMAIL_PATTERN.toRegex()).not()) {
            logger.error("Invalid email address format")
            throw ApiError.BadRequest("Invalid email address")
        }
        if (user.password.length < 8) {
            logger.error("Password must be at least 8 characters long")
            throw ApiError.BadRequest("Password must be at least 8 characters long")
        }
        if (user.firstName.isBlank()) {
            logger.error("First name cannot be empty")
            throw ApiError.BadRequest("First name cannot be empty")
        }
        try {

            val userWithEncryptedPass = user.copy(password = passwordEncoder.encode(user.password))
            return userUseCase.createNewUser(userWithEncryptedPass).toSignupResponse()

        } catch (ex: Exception) {
            logger.error("User with email ${user.email} already exists")
            throw ApiError.DataIntegrityError("User with email ${user.email} already exists", ex)
        } catch (ex: Exception) {
            logger.error("An unexpected error occurred while registering new user", ex)
            throw ApiError.InternalError("Unexpected error occurred", ex)
        }

    }

    fun performUserLogin(email: String, password: String): LoginResponse {
        try {
            val authState = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )
            if (authState.isAuthenticated) {
                logger.info("User with email $email has been authenticated")
                val jwtToken = jwtUtils.generateToken(email)
                return LoginResponse(email = email, token = jwtToken)
            } else {
                logger.error("User with email $email could not be authenticated")
                throw ApiError.Unauthorized("Invalid email or password")
            }
        } catch (ex: BadCredentialsException) {
            logger.error("User with email $email could not be authenticated", ex)
            throw ApiError.Unauthorized("Invalid email or password", ex)
        } catch (ex: Exception) {
            logger.error("An unexpected error occurred while authenticating user with email $email", ex)
            throw ApiError.InternalError("Unexpected error occurred", ex)
        }
    }

}