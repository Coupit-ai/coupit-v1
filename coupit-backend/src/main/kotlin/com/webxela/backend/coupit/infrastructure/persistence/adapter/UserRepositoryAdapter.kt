package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.domain.repo.UserRepository
import com.webxela.backend.coupit.infrastructure.persistence.entity.UserEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.UserEntityMapper.toUser
import com.webxela.backend.coupit.infrastructure.persistence.repo.UserJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(private val userJpaRepo: UserJpaRepo): UserRepository {

    @Transactional
    override fun createNewUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): User {
        val userEntity = UserEntity(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName
        )
        return userJpaRepo.save(userEntity).toUser()
    }

    override fun getUserById(id: Long): User? {
        return userJpaRepo.findUserById(id)?.toUser()
    }

    override fun getUserByEmail(email: String): User? {
        return userJpaRepo.findUserByEmail(email)?.toUser()
    }

    @Transactional
    override fun updateUserPassword(email: String, password: String): Boolean {
        return userJpaRepo.updatePasswordByEmail(email, password) == 0
    }

    @Transactional
    override fun deleteUser(email: String): Boolean {
        return userJpaRepo.deleteByEmail(email) == 0
    }
}