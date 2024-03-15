package com.carmarket.module

import com.carmarket.network.CarMarketApi
import com.carmarket.network.RetrofitInstance
import com.carmarket.repository.AdDetailsRepository
import com.carmarket.repository.AdsByUserRepository
import com.carmarket.repository.AllAdsRepository
import com.carmarket.repository.CreateAdRepository
import com.carmarket.repository.UserRepository
import com.carmarket.ui.adDetails.AdDetailsViewModel
import com.carmarket.ui.adsByUser.AdsByUserViewModel
import com.carmarket.ui.allAds.AllAdsViewModel
import com.carmarket.ui.createAd.CreateAdViewModel
import com.carmarket.ui.login.LoginViewModel
import com.carmarket.ui.registration.RegistrationViewModel
import com.carmarket.ui.userProfile.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { AllAdsViewModel(get()) }
    viewModel { AdDetailsViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { CreateAdViewModel(get()) }
    viewModel { AdsByUserViewModel(get()) }
    viewModel { UserProfileViewModel(get()) }
}

val networkModule = module {
    single { RetrofitInstance.retrofit().create(CarMarketApi::class.java) }
}

val repositoryModule = module {
    single { AllAdsRepository(get()) }
    single { AdDetailsRepository(get()) }
    single { UserRepository(get()) }
    single { CreateAdRepository(get()) }
    single { AdsByUserRepository(get()) }
}