package com.webxela.backend.coupit.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.servlet.http.HttpServletRequest
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class JwtUtils {

    companion object {
        private val logger = LogManager.getLogger(JwtUtils::class.java)
    }

    private val jwtSecret = AppConstants.JWT_SECRET
    private val expiry = AppConstants.JWT_EXPIRY
    private val issuer = AppConstants.JWT_ISSUER

    private fun getKey(): Algorithm? {
        return Algorithm.HMAC512(jwtSecret)
    }

    fun generateToken(subject: String): String {
        return JWT.create()
            .withSubject(subject)
            .withIssuedAt(Instant.now())
            .withIssuer(issuer)
            .withExpiresAt(Instant.now().plus(expiry, ChronoUnit.DAYS))
            .sign(getKey())
    }

    fun validateToken(token: String?, subject: String?): Boolean {
        try {
            if (token.isNullOrBlank()) return false
            if (subject.isNullOrBlank()) return false
            val verifier = JWT.require(getKey())
                .withSubject(subject)
                .withIssuer(issuer)
                .build()
            verifier.verify(token)
            return true
        } catch (ex: Exception) {
            logger.error("Token failed to validate: ${ex.message}", ex)
            return false
        }
    }

    fun getSubjectFromJWT(token: String?): String? {
        try {
            if (token == null) return null
            val verifier = JWT.require(getKey()).build()
            val decodedJWT = verifier.verify(token)
            return decodedJWT.subject
        } catch (ex: Exception) {
            logger.error("Unable to get User from token: ${ex.message}", ex)
            return null
        }
    }

    fun extractJwtFromRequest(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization")
        if (header.isNullOrBlank()) return null
        if (!header.startsWith("Bearer")) return null
        return header.substring(7)
    }
}