package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.domain.repo.DataStoreRepo

class DataStoreUseCase(private val dataStoreRepo: DataStoreRepo) {

    fun saveStringInVault(key: String, value: String): Boolean {
        return dataStoreRepo.saveStringInVault(key, value)
    }

    fun getStringFromVault(key: String): String? {
        return dataStoreRepo.getStringFromVault(key)
    }

    fun checkIfObjectExists(key: String): Boolean {
        return dataStoreRepo.checkIfObjectExists(key)
    }

    fun deleteObject(key: String): Boolean {
        return dataStoreRepo.deleteObject(key)
    }

}