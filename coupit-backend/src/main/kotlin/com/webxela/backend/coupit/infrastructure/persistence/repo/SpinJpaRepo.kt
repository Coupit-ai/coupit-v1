package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface SpinJpaRepo: JpaRepository<SpinEntity, Long> {

    fun findSpinEntityBySpinId(spinId: UUID): SpinEntity?
    fun findSpinEntityBySessionId(sessionId: UUID): SpinEntity?

    @Modifying
    @Query(
        "UPDATE SpinEntity s SET s.claimed = true " +
                "WHERE s.spinId = :spinId AND s.claimed = false"
    )
    fun markSpinAsClaimed(spinId: UUID): Int
    fun deleteBySessionId(sessionId: UUID): Int
}