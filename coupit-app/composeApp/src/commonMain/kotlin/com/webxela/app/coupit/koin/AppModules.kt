package com.webxela.app.coupit.koin

import com.webxela.app.coupit.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module


internal expect val platformModule: Module

val sharedModule = module {

    single { HttpClientFactory.create(engine = get()) }

}