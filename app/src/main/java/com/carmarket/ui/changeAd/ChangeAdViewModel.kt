package com.carmarket.ui.changeAd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carmarket.model.request.AdRequest
import com.carmarket.repository.ChangeAdRepository
import kotlinx.coroutines.launch

class ChangeAdViewModel(
    private val repository: ChangeAdRepository
) : ViewModel() {

    fun changeAd(adRequest: AdRequest, id: Long, bearerToken: String) =
        viewModelScope.launch {
            try {
                repository.changeAd(adRequest, id, bearerToken)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

}