package com.webxela.app.coupit.domain.repo

interface DataStoreRepo {

    fun saveStringInVault(key: String, value: String): Boolean

    fun getStringFromVault(key: String): String?

    fun checkIfObjectExists(key: String): Boolean

    fun deleteObject(key: String): Boolean
}