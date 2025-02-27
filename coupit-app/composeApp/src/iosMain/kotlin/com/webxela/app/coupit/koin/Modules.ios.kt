package com.webxela.app.coupit.koin

import com.liftric.kvault.KVault
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalSettingsImplementation::class)
internal actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<Settings> { KeychainSettings("coupit_securePrefs") }
        single<KVault> {
            KVault(
                serviceName = "webxela",
                accessGroup = "coupit_securePrefs"
            )
        }
    }