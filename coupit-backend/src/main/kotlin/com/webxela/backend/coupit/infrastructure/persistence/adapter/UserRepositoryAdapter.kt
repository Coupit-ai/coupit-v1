package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.domain.repo.UserRepository
import com.webxela.backend.coupit.infrastructure.persistence.mapper.UserEntityMapper.toUser
import com.webxela.backend.coupit.infrastructure.persistence.mapper.UserEntityMapper.toUserEntity
import com.webxela.backend.coupit.infrastructure.persistence.repo.UserJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(private val userJpaRepo: UserJpaRepo): UserRepository {

    @Transactional
    override fun createNewUser(user: User): User {
        return userJpaRepo.save(user.toUserEntity()).toUser()
    }

    override fun getUserById(id: Long): User? {
        return userJpaRepo.findUserById(id)?.toUser()
    }

    override fun getUserByEmail(email: String): User? {
        return userJpaRepo.findUserByEmail(email)?.toUser()
    }

    @Transactional
    override fun updateUserPassword(email: String, password: String): Boolean {
        return userJpaRepo.updatePasswordByEmail(email, password) == 1
    }

    @Transactional
    override fun deleteUser(email: String): Boolean {
        return userJpaRepo.deleteByEmail(email) == 1
    }

    @Transactional
    override fun updateJwtToken(email: String, jwtToken: String?): Boolean {
        return userJpaRepo.updateJwtTokenByEmail(email, jwtToken) == 1
    }
}