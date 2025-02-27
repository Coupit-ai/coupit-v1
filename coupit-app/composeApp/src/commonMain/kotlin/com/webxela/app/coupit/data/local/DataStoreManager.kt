package com.webxela.app.coupit.data.local

import co.touchlab.kermit.Logger
import com.russhwolf.settings.Settings

class DataStoreManager(private val settings: Settings) {

    fun saveStringInVault(key: String, value: String): Boolean {
        return try {
            settings.putString(key, value)
            true
        } catch (e: Exception) {
            Logger.e("Error while saving key $key")
            false
        }
    }

    fun getStringFromVault(key: String): String? {
        return settings.getStringOrNull(key)
    }

    fun deleteObject(key: String): Boolean {
        return if (settings.hasKey(key)) {
            settings.remove(key)
            true
        } else {
            false
        }
    }

    fun clearAll(): Boolean {
        return try {
            settings.clear()
            true
        } catch (e: Exception) {
            Logger.e("Error while deleting all key value pairs")
            false
        }
    }
}
