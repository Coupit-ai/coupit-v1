package com.webxela.backend.coupit.utils

import java.security.MessageDigest
import java.util.UUID

fun generateUniqueIdentifier(): String {
    val uuid = UUID.randomUUID().toString()
    val digest = MessageDigest.getInstance("SHA-1")
    val hashBytes = digest.digest(uuid.toByteArray())
    return bytesToHex(hashBytes)
}

private fun bytesToHex(bytes: ByteArray): String {
    val hexChars = CharArray(bytes.size * 2)
    for (i in bytes.indices) {
        val v = bytes[i].toInt() and 0xFF
        hexChars[i * 2] = "0123456789abcdef"[v ushr 4]
        hexChars[i * 2 + 1] = "0123456789abcdef"[v and 0x0F]
    }
    return String(hexChars)
}
