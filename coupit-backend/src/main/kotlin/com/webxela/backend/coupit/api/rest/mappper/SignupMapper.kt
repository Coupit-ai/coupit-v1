package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.auth.SignupRequest
import com.webxela.backend.coupit.api.rest.dto.auth.SignupResponse
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object SignupMapper {
    fun User.toSignupResponse(): SignupResponse {
        return SignupResponse(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            createdAt = this.createdAt,
            token = this.jwtToken
        )
    }

    fun SignupRequest.toUser(jwtToken: String, passwordEncoder: BCryptPasswordEncoder): User {
        return User(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            jwtToken = jwtToken,
            password = passwordEncoder.encode(this.password)
        )
    }

    fun SquareMerchant.toSignupRequest(): SignupRequest {
        return SignupRequest(
            firstName = this.businessName,
            lastName = "",
            email = this.merchantId,
            password = this.oauthToken.accessToken
        )
    }
}