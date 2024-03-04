package com.carmarket.application

import android.app.Application
import com.carmarket.module.networkModule
import com.carmarket.module.repositoryModule
import com.carmarket.module.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CarMarketApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CarMarketApplication)

            modules(
                listOf(
                    viewModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }

}