package com.carmarket.repository

import com.carmarket.model.request.AdRequest
import com.carmarket.network.CarMarketApi

class ChangeAdRepository(
    private var api: CarMarketApi
) {

    suspend fun changeAd(adRequest: AdRequest, id: Long, bearerToken: String) =
        api.run {
            changeAd(adRequest, id, bearerToken)
        }

}