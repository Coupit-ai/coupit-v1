package com.webxela.backend.coupit.api.rest.controller

import com.webxela.backend.coupit.api.rest.dto.auth.*
import com.webxela.backend.coupit.api.rest.validator.SignupValidator
import com.webxela.backend.coupit.application.service.UserAuthManager
import com.webxela.backend.coupit.common.exception.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userAuthManager: UserAuthManager,
    private val signupValidator: SignupValidator
) {


    @PostMapping("/signup")
    fun registerNewUser(
        @RequestBody signupRequest: SignupRequest
    ): ResponseEntity<ApiResponse<SignupResponse>> {

        signupValidator.validate(signupRequest)
        val signup = userAuthManager.registerNewUser(
            signupRequest.email, signupRequest.password,
            signupRequest.firstName,  signupRequest.lastName
        )
        return ResponseEntity.ok(ApiResponse.success(signup))
    }

    @PostMapping("/login")
    fun performUserLogin(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<ApiResponse<LoginResponse>> {
        val login = userAuthManager.performUserLogin(
            loginRequest.email, loginRequest.password
        )
        return ResponseEntity.ok(ApiResponse.success(login))
    }

    @GetMapping("/logout")
    fun performUserLogout(): ResponseEntity<ApiResponse<LogOutResponse>> {
        val response = LogOutResponse(
            userAuthManager.performUserLogout()
        )
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/reset/{email}")
    fun resetUserPassword(
        @PathVariable email: String
    ) {

    }

}