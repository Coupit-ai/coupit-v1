package com.webxela.backend.coupit.infra.persistence.repo

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infra.persistence.entity.RewardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RewardJpaRepo : JpaRepository<RewardEntity, UUID> {

    fun findByMerchantId(merchantId: String): List<Reward>
}