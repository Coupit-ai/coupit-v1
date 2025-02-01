package com.webxela.app.coupit.koin

import com.liftric.kvault.KVault
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<KVault> {
            KVault(
                serviceName = "com.webxela.app.coupit",
                accessGroup = "coupit_securePrefs"
            )
        }
    }