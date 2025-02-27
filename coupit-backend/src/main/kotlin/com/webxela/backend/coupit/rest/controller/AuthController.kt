package com.webxela.backend.coupit.rest.controller

import com.webxela.backend.coupit.rest.dto.auth.*
import com.webxela.backend.coupit.rest.validator.SignupValidator
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.service.UserAuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val userAuthService: UserAuthService,
    private val signupValidator: SignupValidator
) {


    @PostMapping("/signup")
    fun registerNewUser(
        @RequestBody requestBody: SignupRequest
    ): ResponseEntity<ApiResponse<SignupResponse>> {
        signupValidator.validate(requestBody)
        val signup = userAuthService.registerNewUser(requestBody)
        return ResponseEntity.ok(ApiResponse.success(signup))
    }

    @PostMapping("/login")
    fun performUserLogin(
        @RequestBody requestBody: LoginRequest
    ): ResponseEntity<ApiResponse<LoginResponse>> {
        val login = userAuthService.performUserLogin(
            requestBody.email, requestBody.password
        )
        return ResponseEntity.ok(ApiResponse.success(login))
    }

    @GetMapping("/logout")
    fun performUserLogout(): ResponseEntity<ApiResponse<LogOutResponse>> {
        val response = LogOutResponse(
            userAuthService.performUserLogout()
        )
        return ResponseEntity.ok(ApiResponse.success(response))
    }

}