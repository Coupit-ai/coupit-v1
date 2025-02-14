package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.rest.dto.auth.LoginResponse
import com.webxela.backend.coupit.rest.dto.auth.SignupRequest
import com.webxela.backend.coupit.rest.dto.auth.SignupResponse
import com.webxela.backend.coupit.rest.mappper.SignupDtoMapper.toSignupResponse
import com.webxela.backend.coupit.rest.mappper.SignupDtoMapper.toUser
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import com.webxela.backend.coupit.utils.JwtUtils
import org.apache.logging.log4j.LogManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserAuthService(
    private val userRepo: UserRepoAdapter,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val utilityService: UtilityService
) {

    companion object {
        private val logger = LogManager.getLogger(UserAuthService::class.java)
    }

    @Transactional(readOnly = false)
    fun registerNewUser(signupRequest: SignupRequest): SignupResponse {

        logger.info("Registering new user with email: $signupRequest.email")
        try {

            val jwtToken = jwtUtils.generateToken(signupRequest.email)
            val createdUser = userRepo.createNewUser(
                signupRequest.toUser(jwtToken, passwordEncoder)
            )
            return createdUser.toSignupResponse()

        } catch (ex: Exception) {
            logger.error("User with id $signupRequest.email already exists: ${ex.message}", ex)
            throw ApiError.DataIntegrityError("User with email $signupRequest.email already exists", ex)
        } catch (ex: Exception) {
            logger.error("An unexpected error occurred while registering new user: ${ex.message}", ex)
            throw ApiError.InternalError("Unexpected error occurred", ex)
        }
    }

    @Transactional(readOnly = false)
    fun performUserLogin(email: String, password: String): LoginResponse {
        // Check if user exists
        userRepo.getUserByEmail(email) ?: throw ApiError
            .ResourceNotFound("User with email $email not found")

        try {

            val authState = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )

            if (authState.isAuthenticated) {

                logger.info("User with id $email has been authenticated")
                val jwtToken = jwtUtils.generateToken(email)
                if (userRepo.updateJwtToken(email = email, jwtToken = jwtToken).not())
                    throw RuntimeException("Failed to update JWT token")
                return LoginResponse(email = email, token = jwtToken)

            } else {

                logger.error("User with email $email could not be authenticated")
                throw ApiError.Unauthorized("Invalid email or password")

            }
        } catch (ex: BadCredentialsException) {

            logger.error("User with email $email could not be authenticated: ${ex.message}", ex)
            throw ApiError.Unauthorized("Invalid email or password", ex)

        } catch (ex: Exception) {

            logger.error("An unexpected error occurred while authenticating user with email $email: ${ex.message}", ex)
            throw ApiError.InternalError("Unexpected error occurred", ex)

        }
    }

    @Transactional(readOnly = false)
    fun performUserLogout(): String {

        utilityService.getCurrentLoginUser()?.let { user ->

            userRepo.updateJwtToken(user.username, null)
            SecurityContextHolder.clearContext()

            logger.info("User has been logged out")
            return "User has been logged out"

        } ?: run {

            logger.error("No user is currently logged in")
            throw ApiError.BadRequest("You are not authorised to perform this action")

        }
    }

}