package com.carmarket.module

import com.carmarket.network.CarMarketApi
import com.carmarket.network.RetrofitInstance
import com.carmarket.repository.AdRepository
import com.carmarket.ui.allAds.AllAdsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModule = module {
    viewModel { AllAdsViewModel(get()) }
}

val networkModule = module {
    single { RetrofitInstance.retrofit().create(CarMarketApi::class.java) }
}

val repositoryModule = module {
    single { AdRepository(get()) }
}