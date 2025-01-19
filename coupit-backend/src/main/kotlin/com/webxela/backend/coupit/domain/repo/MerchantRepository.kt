package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.SquareMerchant

interface MerchantRepository {

    fun getMerchantById(merchantId: String): SquareMerchant?

    fun getAllMerchants(): List<SquareMerchant>

    fun addNewMerchant(merchant: SquareMerchant): SquareMerchant

    fun updateMerchant(merchant: SquareMerchant): SquareMerchant

    fun deleteMerchant(merchantId: String): Boolean

}