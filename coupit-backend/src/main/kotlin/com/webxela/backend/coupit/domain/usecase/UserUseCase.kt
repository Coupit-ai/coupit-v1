package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.domain.repo.UserRepository
import org.springframework.stereotype.Component

@Component
class UserUseCase(private val userRepository: UserRepository) {

    fun createNewUser(
        email: String, password: String,
        firstName: String, lastName: String
    ): User {
        return userRepository.createNewUser(email, password, firstName, lastName)
    }

    fun getUserById(id: Long): User? {
        return userRepository.getUserById(id)
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    fun updateUserPassword(email: String, password: String): Boolean {
        return userRepository.updateUserPassword(email, password)
    }

    fun deleteUser(email: String): Boolean {
        return userRepository.deleteUser(email)
    }
}