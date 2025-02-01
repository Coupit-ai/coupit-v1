package com.webxela.app.coupit.data.local

import com.liftric.kvault.KVault

class DataStoreManager(private val kVault: KVault) {

    fun saveStringInVault(key: String, value: String): Boolean {
        return kVault.set(key = key, stringValue = value)
    }

    fun getStringFromVault(key: String): String? {
        return kVault.string(forKey = key)
    }

    fun checkIfObjectExists(key: String): Boolean {
        return kVault.existsObject(forKey = key)
    }

    fun deleteObject(key: String): Boolean {
        return kVault.deleteObject(forKey = key)
    }
}