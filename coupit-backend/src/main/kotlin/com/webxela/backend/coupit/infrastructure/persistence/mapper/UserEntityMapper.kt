package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.infrastructure.persistence.entity.UserEntity

object UserEntityMapper {

    fun User.toUserEntity(): UserEntity {
        return UserEntity(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password
        )
    }

    fun UserEntity.toUser(): User {
        return User(
            id = id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password,
            createdAt = this.createdAt
        )
    }
}