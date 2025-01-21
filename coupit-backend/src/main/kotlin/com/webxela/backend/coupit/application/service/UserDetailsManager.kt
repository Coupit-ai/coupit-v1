package com.webxela.backend.coupit.application.service

import com.webxela.backend.coupit.domain.usecase.UserUseCase
import org.apache.logging.log4j.LogManager
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class UserDetailsManager(private val userUseCase: UserUseCase) : UserDetailsService {

    companion object {
        private val logger = LogManager.getLogger(UserDetailsService::class.java)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userUseCase.getUserByEmail(it)
            if (user != null) {
                return User.builder()
                    .username(user.email)
                    .password(user.password)
                    .authorities(SimpleGrantedAuthority("USER"))
                    .build()
            }
        }
        logger.error("User $username not found")
        throw UsernameNotFoundException("User with $username not found")
    }
}