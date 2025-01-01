package com.webxela.backend.coupit.infrastructure.config

import com.webxela.backend.coupit.domain.repo.SessionRepo
import com.webxela.backend.coupit.domain.usecase.SessionUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainUseCaseConfig(
    private val sessionRepo: SessionRepo
) {

    @Bean
    fun sessionUseCase(): SessionUseCase {
        return SessionUseCase(sessionRepo)
    }
}