package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepo: JpaRepository<MerchantEntity, Long> {

    fun findByMerchantId(merchantId: String): MerchantEntity?

    fun deleteByMerchantId(merchantId: String): Int

}