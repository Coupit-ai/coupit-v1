package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.infra.persistence.entity.UserEntity
import com.webxela.backend.coupit.infra.persistence.mapper.OauthEntityMapper.toOauthEntity
import com.webxela.backend.coupit.infra.persistence.mapper.OauthEntityMapper.toOauthToken

object UserEntityMapper {

    fun UserEntity.toUser(): User {
        return User(
            id = id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password,
            jwtToken = this.jwtToken,
            createdAt = this.createdAt,
            oauthToken = this.oauthTokenEntity?.toOauthToken(),
            fcmToken = this.fcmToken,
            deviceType = this.deviceType
        )
    }

    fun User.toUserEntity(): UserEntity {
        return UserEntity(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            password = this.password,
            jwtToken = this.jwtToken,
            oauthTokenEntity = this.oauthToken?.toOauthEntity(),
            fcmToken = this.fcmToken
        )
    }
}