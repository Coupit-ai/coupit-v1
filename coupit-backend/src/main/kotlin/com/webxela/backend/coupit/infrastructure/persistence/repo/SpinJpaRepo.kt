package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SpinJpaRepo: JpaRepository<SpinEntity, Long> {

    fun findSpinEntityBySpinId(spinId: String): SpinEntity?
    fun findSpinEntityBySessionId(sessionId: String): SpinEntity?

    @Modifying
    @Query(
        "UPDATE SpinEntity s SET s.claimed = true " +
                "WHERE s.spinId = :spinId AND s.claimed = false"
    )
    fun markSpinAsClaimed(spinId: String): Int
    fun deleteBySessionId(sessionId: String): Int
}