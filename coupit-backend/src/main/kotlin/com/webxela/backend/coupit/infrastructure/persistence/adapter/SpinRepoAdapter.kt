package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.repo.SpinRepo
import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OfferEntityMapper.toOfferEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SpinEntityMapper.toSpinResult
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SpinEntityMapper.toSpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.repo.OfferJpaRepo
import com.webxela.backend.coupit.infrastructure.persistence.repo.SpinJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*

@Component
@Transactional
class SpinRepoAdapter(
    private val spinJpaRepo: SpinJpaRepo,
    private val offerJpaRepo: OfferJpaRepo
) : SpinRepo {

    override fun getSpinResultBySpinId(spinId: UUID): SpinResult? {
        return spinJpaRepo.findSpinEntityBySpinId(spinId)?.toSpinResult()
    }

    override fun getSpinResultBySessionId(sessionId: UUID): SpinResult? {
        return spinJpaRepo.findSpinEntityBySessionId(sessionId)?.toSpinResult()
    }

    override fun saveSpinResult(merchantId: String, offerId: UUID, qrCode: String, sessionId: UUID): SpinResult {
        val offer = offerJpaRepo.findOfferByOfferId(offerId)
            ?: throw IllegalStateException("Offer not found in database")

        val spinEntity = SpinEntity(
            merchantId = merchantId,
            offer = offer,
            qrCode = qrCode,
            sessionId = sessionId
        )

        return spinJpaRepo.save(spinEntity).toSpinResult()
    }

    override fun markSpinAsClaimed(spinId: UUID): Boolean {
        return when (spinJpaRepo.markSpinAsClaimed(spinId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as claimed")
        }
    }

    override fun deleteSpinResult(sessionId: UUID): Boolean {
        return when(spinJpaRepo.deleteBySessionId(sessionId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows are deleted")
        }
    }
}