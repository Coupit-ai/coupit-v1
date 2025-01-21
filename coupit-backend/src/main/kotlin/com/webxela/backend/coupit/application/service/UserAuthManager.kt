package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.api.rest.dto.auth.LoginResponse
import com.webxela.backend.coupit.api.rest.dto.auth.SignupRequest
import com.webxela.backend.coupit.api.rest.dto.auth.SignupResponse
import com.webxela.backend.coupit.api.rest.mappper.SignupMapper.toSignupResponse
import com.webxela.backend.coupit.api.rest.mappper.SignupMapper.toUser
import com.webxela.backend.coupit.common.exception.ApiError
import com.webxela.backend.coupit.common.utils.JwtUtils
import com.webxela.backend.coupit.domain.usecase.UserUseCase
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserAuthManager(
    private val userUseCase: UserUseCase,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val utilityService: UtilityService
) {

    companion object {
        private val logger = LogManager.getLogger(UserAuthManager::class.java)
    }

    @Transactional
    fun registerNewUser(signupRequest: SignupRequest): SignupResponse {
        logger.info("Registering new user with email: $signupRequest.email")
        try {
            val jwtToken = jwtUtils.generateToken(signupRequest.email)
            val createdUser = userUseCase.createNewUser(
                signupRequest.toUser(jwtToken, passwordEncoder)
            )
            return createdUser.toSignupResponse()
        } catch (ex: Exception) {
            logger.error("User with id $signupRequest.email already exists", ex)
            throw ApiError.DataIntegrityError("User with email $signupRequest.email already exists", ex)
        } catch (ex: Exception) {
            logger.error("An unexpected error occurred while registering new user", ex)
            throw ApiError.InternalError("Unexpected error occurred", ex)
        }
    }

    @Transactional
    fun performUserLogin(email: String, password: String): LoginResponse {
        // Check if user exists
        userUseCase.getUserByEmail(email) ?: throw ApiError.ResourceNotFound(
            "User with email $email not found"
        )
        try {
            val authState = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )
            if (authState.isAuthenticated) {
                logger.info("User with id $email has been authenticated")
                val jwtToken = jwtUtils.generateToken(email)
                if (userUseCase.updateJwtToken(email, jwtToken).not())
                    throw RuntimeException("Failed to update JWT token")
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

    @Transactional
    fun performUserLogout(): String {
        val user = utilityService.getCurrentLoginUser()
        if (user != null) {
            userUseCase.updateJwtToken(user.username, null)
            SecurityContextHolder.clearContext()
            logger.info("User has been logged out")
            return "User has been logged out"
        } else {
            logger.error("No user is currently logged in")
            throw ApiError.Unauthorized("You are not authorised to perform this action")
        }
    }

}