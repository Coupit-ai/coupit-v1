package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.repo.MerchantRepository
import org.springframework.stereotype.Component

@Component
class MerchantUseCase(private val merchantRepository: MerchantRepository) {

    fun getMerchantById(merchantId: String): SquareMerchant? {
        return merchantRepository.getMerchantById(merchantId)
    }

    fun addNewMerchant(merchant: SquareMerchant): SquareMerchant {
        return merchantRepository.addNewMerchant(merchant)
    }

    fun updateMerchant(merchant: SquareMerchant): SquareMerchant {
        return merchantRepository.updateMerchant(merchant)
    }

    fun deleteMerchant(merchantId: String): Boolean {
        return merchantRepository.deleteMerchant(merchantId)
    }
}