package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.RewardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RewardJpaRepo : JpaRepository<RewardEntity, Long> {

    fun findByRewardId(rewardId: String): RewardEntity?
}