package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.repo.MerchantRepository
import com.webxela.backend.coupit.infrastructure.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.MerchantEntityMapper.toSquareMerchant
import com.webxela.backend.coupit.infrastructure.persistence.repo.MerchantJpaRepo
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

@Component
class MerchantRepositoryAdapter(private val merchantJpaRepo: MerchantJpaRepo) : MerchantRepository {

    companion object {
        val logger: Logger = LogManager.getLogger(MerchantRepositoryAdapter::class.java)
    }

    override fun getMerchantById(merchantId: String): SquareMerchant? {
        return merchantJpaRepo.findByMerchantId(merchantId)?.toSquareMerchant()
    }

    @Transactional
    override fun addNewMerchant(merchant: SquareMerchant): SquareMerchant {
        return merchantJpaRepo.save(merchant.toMerchantEntity()).toSquareMerchant()
    }

    @Transactional
    override fun updateMerchant(merchant: SquareMerchant): SquareMerchant {
        val deleted = merchantJpaRepo.deleteByMerchantId(merchant.merchantId)
        if (deleted <= 1) {
            merchantJpaRepo.flush() // commit delete operation
            return merchantJpaRepo.save(merchant.toMerchantEntity()).toSquareMerchant()
        } else {
            logger.error("Cant update the merchant with id ${merchant.merchantId}")
            throw RuntimeException("Cant update the merchant with id ${merchant.merchantId}")
        }
    }

    @Transactional
    override fun deleteMerchant(merchantId: String): Boolean {
        return merchantJpaRepo.deleteByMerchantId(merchantId) == 1
    }

}