package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toSquareMerchant
import com.webxela.backend.coupit.infra.persistence.repo.MerchantJpaRepo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MerchantRepoAdapter(private val merchantJpaRepo: MerchantJpaRepo) {

    companion object {
        val logger: Logger = LogManager.getLogger(MerchantRepoAdapter::class.java)
    }

    @Transactional(readOnly = true)
    fun getMerchant(merchantId: String): SquareMerchant? {
        return merchantJpaRepo.findById(merchantId).map { it.toSquareMerchant() }.orElse(null)
    }

    @Transactional(readOnly = false)
    fun saveOrUpdateMerchant(merchant: SquareMerchant): SquareMerchant {
        return merchantJpaRepo.save(merchant.toMerchantEntity()).toSquareMerchant()
    }

    @Transactional(readOnly = false)
    fun deleteMerchant(merchantId: String) {
        return merchantJpaRepo.deleteById(merchantId)
    }

}