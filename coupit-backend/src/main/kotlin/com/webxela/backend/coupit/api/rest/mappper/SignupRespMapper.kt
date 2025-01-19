package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.auth.SignupResponse
import com.webxela.backend.coupit.domain.model.User

object SignupRespMapper {
    fun User.toSignupResponse(): SignupResponse {
        return SignupResponse(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            createdAt = this.createdAt,
            token = this.jwtToken
        )
    }
}