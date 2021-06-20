package com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase.SpaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class StationViewModel
@Inject constructor(var spaceUseCase: SpaceUseCase) : ViewModel() {


    val stationLiveData: MutableLiveData<List<Station>?> by lazy { MutableLiveData<List<Station>?>() }

    val updateMissionLiveData: MutableLiveData<Station?> by lazy { MutableLiveData<Station?>() }
    val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val error: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }

    fun getStationList() {

        loading.value = true
        viewModelScope.launch {
            try {
                stationLiveData.value=spaceUseCase.getStationList()
                loading.value=false
            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }


    fun updateMission(station:Station) {

        loading.value = true
        viewModelScope.launch {
            try {
                updateMissionLiveData.value=spaceUseCase.updateMission(station)
                loading.value=false
            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }


}