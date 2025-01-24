package com.webxela.backend.coupit.infra.persistence.repo

import com.webxela.backend.coupit.infra.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserJpaRepo : JpaRepository<UserEntity, UUID> {

    fun findUserByEmail(email: String): UserEntity?

    fun deleteByEmail(email: String): Int
}