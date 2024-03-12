package com.carmarket.module

import com.carmarket.network.CarMarketApi
import com.carmarket.network.RetrofitInstance
import com.carmarket.repository.AdDetailsRepository
import com.carmarket.repository.AllAdsRepository
import com.carmarket.repository.UserRepository
import com.carmarket.ui.adDetails.AdDetailsViewModel
import com.carmarket.ui.allAds.AllAdsViewModel
import com.carmarket.ui.login.LoginViewModel
import com.carmarket.ui.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { AllAdsViewModel(get()) }
    viewModel { AdDetailsViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}

val networkModule = module {
    single { RetrofitInstance.retrofit().create(CarMarketApi::class.java) }
}

val repositoryModule = module {
    single { AllAdsRepository(get()) }
    single { AdDetailsRepository(get()) }
    single { UserRepository(get()) }
}