package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.infrastructure.persistence.entity.UserEntity

object UserEntityMapper {

    fun UserEntity.toUser(): User {
        return User(
            id = id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password,
            jwtToken = this.jwtToken
        )
    }
}