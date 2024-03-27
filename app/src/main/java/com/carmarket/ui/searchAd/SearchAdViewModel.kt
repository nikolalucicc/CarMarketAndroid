package com.carmarket.ui.searchAd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.repository.SearchAdRepository
import com.carmarket.stateClasses.AdUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchAdViewModel(
    private val repository: SearchAdRepository
) : ViewModel() {

    private val adUIState: MutableStateFlow<AdUIState> = MutableStateFlow(AdUIState.Loading)
    val adUiDataState: StateFlow<AdUIState> = adUIState

    fun searchAd(
        brand: String?,
        model: String?,
        carBody: String?,
        minYear: Int?,
        maxYear: Int?,
        minPrice: Int?,
        maxPrice: Int?,
        condition: String?,
        minMileage: Int?,
        maxMileage: Int?,
        fuel: String?,
        minEngineHp: Int?,
        maxEngineHp: Int?,
        minEngineKw: Int?,
        maxEngineKw: Int?,
        minEngineCubic: Int?,
        maxEngineCubic: Int?,
        transmission: String?,
        city: String?,
        country: String?,
        drive: String?,
        doorNumber: String?,
        seatsNumber: String?,
        wheelSide: String?,
        airConditioning: String?,
        color: String?,
        interiorColor: String?,
        registeredUntil: String?,
        phoneNumber: String?,
        bearerToken: String
    ) {
        viewModelScope.launch {
            adUIState.value = AdUIState.Loading
            runCatching {
                repository.searchAd(
                    brand,
                    model,
                    carBody,
                    minYear,
                    maxYear,
                    minPrice,
                    maxPrice,
                    condition,
                    minMileage,
                    maxMileage,
                    fuel,
                    minEngineHp,
                    maxEngineHp,
                    minEngineKw,
                    maxEngineKw,
                    minEngineCubic,
                    maxEngineCubic,
                    transmission,
                    city,
                    country,
                    drive,
                    doorNumber,
                    seatsNumber,
                    wheelSide,
                    airConditioning,
                    color,
                    interiorColor,
                    registeredUntil,
                    phoneNumber,
                    bearerToken
                )
            }.mapCatching { ads ->
                Log.d("rezultati", ads.size.toString())
                AdUIState.Success(ads)
            }.getOrElse { exception ->
                AdUIState.Error(exception.message ?: "Unknown error")
            }.also { state ->
                adUIState.value = state
            }
        }
    }
}
