package com.webxela.app.coupit.domain.repo

interface DataStoreRepo {

    fun saveStringInVault(key: String, value: String): Boolean

    fun getStringFromVault(key: String): String?

    fun clearAllObjects(): Boolean

    fun deleteObject(key: String): Boolean
}