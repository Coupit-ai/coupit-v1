package com.webxela.app.coupit.koin

import com.webxela.app.coupit.core.data.HttpClientFactory
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.data.local.DataStoreManager
import com.webxela.app.coupit.data.remote.FirebaseManager
import com.webxela.app.coupit.data.remote.SpinManager
import com.webxela.app.coupit.data.remote.SquareManager
import com.webxela.app.coupit.data.repo.DataStoreRepoImpl
import com.webxela.app.coupit.data.repo.FirebaseRepoImpl
import com.webxela.app.coupit.data.repo.SpinRepoImpl
import com.webxela.app.coupit.data.repo.SquareRepoImpl
import com.webxela.app.coupit.domain.repo.DataStoreRepo
import com.webxela.app.coupit.domain.repo.FirebaseRepo
import com.webxela.app.coupit.domain.repo.SpinRepo
import com.webxela.app.coupit.domain.repo.SquareRepo
import com.webxela.app.coupit.domain.usecase.DataStoreUseCase
import com.webxela.app.coupit.domain.usecase.FirebaseUseCase
import com.webxela.app.coupit.domain.usecase.SpinUseCase
import com.webxela.app.coupit.domain.usecase.SquareUseCase
import com.webxela.app.coupit.presentation.features.auth.viewmodel.AuthViewModel
import com.webxela.app.coupit.presentation.features.firebase.FirebaseService
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

    single {
        HttpClientFactory.create(
            engine = get(),
            jwtTokenProvider = { get<SquareUseCase>().getJwtToken() }
        )
    }

    singleOf(::SpinManager)
    singleOf(::SquareManager)
    singleOf(::DataStoreManager)
    singleOf(::FirebaseManager)

    singleOf(::SpinRepoImpl).bind<SpinRepo>()
    singleOf(::SquareRepoImpl).bind<SquareRepo>()
    singleOf(::DataStoreRepoImpl).bind<DataStoreRepo>()
    singleOf(::FirebaseRepoImpl).bind<FirebaseRepo>()

    singleOf(::SpinUseCase)
    singleOf(::SquareUseCase)
    singleOf(::DataStoreUseCase)
    singleOf(::FirebaseUseCase)

    singleOf(::FirebaseService)

    viewModelOf(::WheelViewModel)
    viewModelOf(::RewardViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AuthViewModel)
}