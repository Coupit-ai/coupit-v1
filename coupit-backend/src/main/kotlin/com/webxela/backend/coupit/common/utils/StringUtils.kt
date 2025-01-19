package com.webxela.backend.coupit.common.utils

fun String.toList(): List<String> {
    if (this.isBlank() || this == "[]") return emptyList()
    return this.removeSurrounding("[", "]")
        .split(",")
        .map { it.trim() }
}