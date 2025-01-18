package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.auth.SignupRequest
import com.webxela.backend.coupit.api.rest.dto.auth.SignupResponse
import com.webxela.backend.coupit.domain.model.User

object AuthMapper {

    fun SignupRequest.toUser(): User {
        return User(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password
        )
    }

    fun User.toSignupResponse(): SignupResponse {
        return SignupResponse(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            createdAt = this.createdAt
        )
    }
}