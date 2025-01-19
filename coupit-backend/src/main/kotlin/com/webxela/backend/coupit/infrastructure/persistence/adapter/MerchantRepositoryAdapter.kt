package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.repo.MerchantRepository
import com.webxela.backend.coupit.infrastructure.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.MerchantEntityMapper.toSquareMerchant
import com.webxela.backend.coupit.infrastructure.persistence.repo.MerchantJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class MerchantRepositoryAdapter(private val merchantJpaRepo: MerchantJpaRepo) : MerchantRepository {

    override fun getMerchantById(merchantId: String): SquareMerchant? {
        return merchantJpaRepo.findByMerchantId(merchantId)?.toSquareMerchant()
    }

    override fun getAllMerchants(): List<SquareMerchant> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun addNewMerchant(merchant: SquareMerchant): SquareMerchant {
        return merchantJpaRepo.save(merchant.toMerchantEntity()).toSquareMerchant()
    }

    @Transactional
    override fun updateMerchant(merchant: SquareMerchant): SquareMerchant {
        val deleted = merchantJpaRepo.deleteByMerchantId(merchant.merchantId)
        if (deleted == 1) {
            merchantJpaRepo.flush() // commit delete ops
            return merchantJpaRepo.save(merchant.toMerchantEntity()).toSquareMerchant()
        }
        throw RuntimeException("Cant update the merchant with id ${merchant.merchantId}")
    }

    override fun deleteMerchant(merchantId: String): Boolean {
        TODO("Not yet implemented")
    }
}