package com.webxela.app.coupit.koin

import com.webxela.app.coupit.core.data.HttpClientFactory
import com.webxela.app.coupit.data.remote.SessionManager
import com.webxela.app.coupit.data.remote.SpinManager
import com.webxela.app.coupit.data.remote.SquareManager
import com.webxela.app.coupit.data.repo.SessionRepoImpl
import com.webxela.app.coupit.data.repo.SpinRepoImpl
import com.webxela.app.coupit.data.repo.SquareRepoImpl
import com.webxela.app.coupit.domain.repo.SessionRepo
import com.webxela.app.coupit.domain.repo.SpinRepo
import com.webxela.app.coupit.domain.repo.SquareRepo
import com.webxela.app.coupit.domain.usecase.SessionUseCase
import com.webxela.app.coupit.domain.usecase.SpinUseCase
import com.webxela.app.coupit.domain.usecase.SquareUseCase
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeViewModel
import com.webxela.app.coupit.presentation.features.reward.viewmodel.RewardViewModel
import com.webxela.app.coupit.presentation.features.wheel.viewmodel.WheelViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal expect val platformModule: Module

val sharedModule = module {

    single { HttpClientFactory.create(engine = get()) }

    singleOf(::SessionManager)
    singleOf(::SpinManager)
    singleOf(::SquareManager)

    singleOf(::SessionRepoImpl).bind<SessionRepo>()
    singleOf(::SpinRepoImpl).bind<SpinRepo>()
    singleOf(::SquareRepoImpl).bind<SquareRepo>()

    singleOf(::SessionUseCase)
    singleOf(::SpinUseCase)
    singleOf(::SquareUseCase)

    viewModelOf(::WheelViewModel)
    viewModelOf(::RewardViewModel)
    viewModelOf(::HomeViewModel)

}