package com.webxela.backend.coupit.application.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UtilityService {

    fun getCurrentLoginUser(): UserDetails? {
        val authState = SecurityContextHolder.getContext().authentication
        if (authState != null && authState.principal != "anonymousUser"){
            return (authState.principal as UserDetails)
        }
        return null
    }
}