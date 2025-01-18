package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.domain.repo.UserRepo
import org.springframework.stereotype.Component

@Component
class UserUseCase(private val userRepo: UserRepo) {

        fun createNewUser(user: User): User {
            return userRepo.createNewUser(user)
        }

        fun getUserById(id: Long): User? {
            return userRepo.getUserById(id)
        }

        fun getUserByEmail(email: String): User? {
            return userRepo.getUserByEmail(email)
        }

        fun updateUserPassword(email: String, password: String): Boolean {
            return userRepo.updateUserPassword(email, password)
        }

        fun deleteUser(email: String): Boolean {
            return userRepo.deleteUser(email)
        }
}