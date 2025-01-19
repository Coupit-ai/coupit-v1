package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface UserJpaRepo : JpaRepository<UserEntity, Long> {

    fun findUserByEmail(email: String): UserEntity?
    fun findUserById(id: Long): UserEntity?
    fun deleteByEmail(email: String): Int

    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.email = :email")
    fun updatePasswordByEmail(email: String, password: String): Int

    @Modifying
    @Query("UPDATE UserEntity u SET u.jwtToken = :jwtToken WHERE u.email = :email")
    fun updateJwtTokenByEmail(email: String, jwtToken: String?): Int
}