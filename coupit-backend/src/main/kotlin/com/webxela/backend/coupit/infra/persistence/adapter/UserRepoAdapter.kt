package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.infra.persistence.mapper.OauthEntityMapper.toOauthEntity
import com.webxela.backend.coupit.infra.persistence.mapper.UserEntityMapper.toUser
import com.webxela.backend.coupit.infra.persistence.mapper.UserEntityMapper.toUserEntity
import com.webxela.backend.coupit.infra.persistence.repo.UserJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class UserRepoAdapter(private val userJpaRepo: UserJpaRepo) {

    @Transactional
    fun createNewUser(user: User): User {
        return userJpaRepo.saveAndFlush(user.toUserEntity()).toUser()
    }

    fun getUserByEmail(email: String): User? {
        return userJpaRepo.findUserByEmail(email)?.toUser()
    }

    @Transactional
    fun deleteUser(email: String): Boolean {
        return userJpaRepo.deleteByEmail(email) == 1
    }

    @Transactional
    fun updateJwtToken(email: String, jwtToken: String?): Boolean {
        val user = userJpaRepo.findUserByEmail(email) ?: return false
        val updatedUser = user.copy(jwtToken = jwtToken)
        userJpaRepo.saveAndFlush(updatedUser)
        return true
    }

    @Transactional
    fun updateOauthToken(email: String, oauthToken: OauthToken?): Boolean {
        val user = userJpaRepo.findUserByEmail(email) ?: return false
        val updatedUser = user.copy(oauthTokenEntity = oauthToken?.toOauthEntity())
        userJpaRepo.saveAndFlush(updatedUser)
        return true
    }
}