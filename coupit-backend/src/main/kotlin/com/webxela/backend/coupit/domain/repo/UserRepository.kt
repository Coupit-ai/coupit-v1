package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.User

interface UserRepository {

    fun createNewUser(
        email: String, password: String,
        firstName: String, lastName: String
    ): User

    fun getUserById(id: Long): User?
    fun getUserByEmail(email: String): User?
    fun updateUserPassword(email: String, password: String): Boolean
    fun deleteUser(email: String): Boolean
}