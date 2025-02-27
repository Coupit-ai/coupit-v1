package com.webxela.app.coupit.koin

import com.liftric.kvault.KVault
import com.russhwolf.settings.Settings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<KVault> {
            KVault(
                context = get(),
                fileName = "coupit_securePrefs"
            )
        }
    }