package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.data.local.DataStoreManager
import com.webxela.app.coupit.domain.repo.DataStoreRepo

class DataStoreRepoImpl(private val dataStoreManager: DataStoreManager) : DataStoreRepo {

    override fun saveStringInVault(key: String, value: String): Boolean {
        return dataStoreManager.saveStringInVault(key, value)
    }

    override fun getStringFromVault(key: String): String? {
        return dataStoreManager.getStringFromVault(key)
    }

    override fun clearAllObjects(): Boolean {
        return dataStoreManager.clearAll()
    }

    override fun deleteObject(key: String): Boolean {
        return dataStoreManager.deleteObject(key)
    }
}