package com.webxela.backend.coupit.api.mappper

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID

object UserDtoMapper {

    fun SquareMerchant.toUser(
        jwtToken: String,
        passwordEncoder: BCryptPasswordEncoder,
        oauthToken: OauthToken
    ): User {
        return User(
            email = this.id,
            password = passwordEncoder.encode(UUID.randomUUID().toString()),
            firstName = this.businessName,
            lastName = null,
            createdAt = null,
            jwtToken = jwtToken,
            oauthToken = oauthToken
        )
    }
}