package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.domain.model.User
import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import com.webxela.backend.coupit.utils.AppConstants
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.net.URLEncoder

@Service
class UtilityService(private val userRepo: UserRepoAdapter) {

    fun getCurrentLoginUser(): UserDetails? {
        val authState = SecurityContextHolder.getContext().authentication
        println(authState)
        if (authState != null && authState.principal != "anonymousUser") {
            return (authState.principal as UserDetails)
        }
        return null
    }

    fun getUserIfExistsOrConnected(email: String): User? {
        userRepo.getUserByEmail(email)?.let { user ->
            return if (user.jwtToken.isNullOrBlank() || user.oauthToken == null) null
            else user
        }
        return null
    }

    fun buildSuccessUri(token: String, state: String): String {
        return AppConstants.DEEPLINK_URI +
                "?token=${URLEncoder.encode(token, "UTF-8")}" +
                "&state=${URLEncoder.encode(state, "UTF-8")}"
    }

    fun buildErrorUri(errorMessage: String): String {
        return AppConstants.DEEPLINK_URI +
                "?error=${URLEncoder.encode(errorMessage, "UTF-8")}"
    }
}