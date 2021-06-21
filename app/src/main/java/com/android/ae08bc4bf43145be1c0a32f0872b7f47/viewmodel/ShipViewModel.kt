package com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase.SpaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ShipViewModel
@Inject constructor(var spaceUseCase: SpaceUseCase) : ViewModel() {

    val shipInfoLiveData: MutableLiveData<Ship> by lazy { MutableLiveData<Ship>() }
    val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val error: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }

    fun saveShipInfo(ship: Ship) {
        loading.value = true
        viewModelScope.launch {
            try {
                shipInfoLiveData.value = spaceUseCase.saveShipInfo(ship)
                loading.value = false
            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }



}