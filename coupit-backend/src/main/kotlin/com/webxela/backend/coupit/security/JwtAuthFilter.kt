package com.webxela.backend.coupit.security

import com.webxela.backend.coupit.infra.persistence.adapter.UserRepoAdapter
import com.webxela.backend.coupit.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtils: JwtUtils,
    private val userDetailsService: UserDetailsService,
    private val userRepoAdapter: UserRepoAdapter
) : OncePerRequestFilter() {

    companion object {
        val myLogger: Logger = LogManager.getLogger(JwtAuthFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val jwtToken = jwtUtils.extractJwtFromRequest(request)
        if (jwtToken.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }
        val subject = jwtUtils.getSubjectFromJWT(jwtToken)
        val authState = SecurityContextHolder.getContext().authentication

        if (subject.isNullOrBlank().not() && authState == null) {

            val dbUserData = userRepoAdapter.getUserByEmail(subject!!)
            if (dbUserData == null || dbUserData.jwtToken != jwtToken) {
                myLogger.error("Mismatch between provided JWT token and the one stored in the database")
                filterChain.doFilter(request, response)
                return
            }

            val userDetails = userDetailsService.loadUserByUsername(subject)

            if (jwtUtils.validateToken(jwtToken, userDetails.username)) {
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }

        } else myLogger.error("JWT token is invalid or expired")

        filterChain.doFilter(request, response)
    }
}