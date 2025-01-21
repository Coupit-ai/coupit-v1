package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.User

interface UserRepository {

    fun createNewUser(user: User): User

    fun getUserById(id: Long): User?

    fun getUserByEmail(email: String): User?

    fun updateUserPassword(email: String, password: String): Boolean

    fun deleteUser(email: String): Boolean

    fun updateJwtToken(email: String, jwtToken: String?): Boolean
}