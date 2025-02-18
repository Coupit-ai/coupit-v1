package com.webxela.app.coupit.core.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
actual fun getWindowSizeClass(): WindowWidthSizeClass {
    val adaptiveWindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    return when (adaptiveWindowSizeClass) {
        androidx.window.core.layout.WindowWidthSizeClass.COMPACT -> WindowWidthSizeClass.Compact
        androidx.window.core.layout.WindowWidthSizeClass.MEDIUM-> WindowWidthSizeClass.Medium
        androidx.window.core.layout.WindowWidthSizeClass.EXPANDED -> WindowWidthSizeClass.Expanded
        else -> WindowWidthSizeClass.Compact
    }
}