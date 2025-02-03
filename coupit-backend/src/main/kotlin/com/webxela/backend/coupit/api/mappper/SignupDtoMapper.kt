package com.webxela.backend.coupit.api.mappper

import com.webxela.backend.coupit.api.dto.auth.SignupRequest
import com.webxela.backend.coupit.api.dto.auth.SignupResponse
import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID

object SignupDtoMapper {
    fun User.toSignupResponse(): SignupResponse {
        return SignupResponse(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            createdAt = this.createdAt!!,
            token = this.jwtToken!!
        )
    }

    fun SignupRequest.toUser(
        jwtToken: String,
        passwordEncoder: BCryptPasswordEncoder,
        oauthToken: OauthToken? = null
    ): User {
        return User(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            jwtToken = jwtToken,
            password = passwordEncoder.encode(this.password),
            oauthToken = oauthToken,
            createdAt = null,
            fcmToken = null,
            deviceType = null
        )
    }

    fun SquareMerchant.toSignupRequest(): SignupRequest {
        return SignupRequest(
            firstName = this.businessName,
            lastName = null,
            email = this.id,
            password = UUID.randomUUID().toString() // Generate a random password
        )
    }
}