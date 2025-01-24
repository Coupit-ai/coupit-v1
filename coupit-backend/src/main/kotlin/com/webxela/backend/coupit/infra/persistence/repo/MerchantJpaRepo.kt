package com.webxela.backend.coupit.infra.persistence.repo

import com.webxela.backend.coupit.infra.persistence.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MerchantJpaRepo: JpaRepository<MerchantEntity, String>