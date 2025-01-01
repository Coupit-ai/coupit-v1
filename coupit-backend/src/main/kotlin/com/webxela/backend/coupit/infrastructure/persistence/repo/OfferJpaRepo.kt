package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.OfferEntity
import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OfferJpaRepo : JpaRepository<OfferEntity, Long> {

}