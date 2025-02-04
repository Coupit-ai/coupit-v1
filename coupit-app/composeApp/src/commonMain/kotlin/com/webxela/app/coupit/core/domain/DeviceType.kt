package com.webxela.app.coupit.core.domain


expect fun getDeviceType(): DeviceType

enum class DeviceType {
    ANDROID,
    IOS
}