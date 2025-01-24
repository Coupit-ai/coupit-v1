package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import org.apache.logging.log4j.LogManager
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepoAdapter: UserRepoAdapter) : UserDetailsService {

    companion object {
        private val logger = LogManager.getLogger(UserDetailsService::class.java)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepoAdapter.getUserByEmail(it)
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