package com.carmarket.module

import com.carmarket.network.CarMarketApi
import com.carmarket.network.RetrofitInstance
import org.koin.dsl.module

val viewModule = module {

}

val networkModule = module {
    single { RetrofitInstance.retrofit().create(CarMarketApi::class.java) }
}

val repositoryModule = module {

}