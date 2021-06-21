package com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase.SpaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class StationViewModel
@Inject constructor(var spaceUseCase: SpaceUseCase) : ViewModel() {

    private var searchJob: Job? = null
    val searchLiveData: MutableLiveData<ArrayList<Station>?> by lazy { MutableLiveData<ArrayList<Station>?>() }
    val stationLiveData: MutableLiveData<List<Station>?> by lazy { MutableLiveData<List<Station>?>() }
    val favOperationLiveData: MutableLiveData<Station> by lazy { MutableLiveData<Station>() }
    val shipInfoLiveData: MutableLiveData<Ship> by lazy { MutableLiveData<Ship>() }
    val updateShipInfoLiveData: MutableLiveData<Ship> by lazy { MutableLiveData<Ship>() }
    val updateMissionLiveData: MutableLiveData<Station?> by lazy { MutableLiveData<Station?>() }
    val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val error: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }


    fun onSearchQuery(value: String) {
        searchJob = viewModelScope.launch() {
            try {
                searchLiveData.value = spaceUseCase.searchItem(value)
            } catch (e: Exception) {
                error.value = e
            }


        }
    }

    fun favoriteOperation(addToFav: Boolean, station: Station) {
        viewModelScope.launch {
            try {
                favOperationLiveData.value = spaceUseCase.favoriteOperation(addToFav, station)

            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }

    fun getStationList() {
        loading.value = true
        viewModelScope.launch {
            try {
                stationLiveData.value = spaceUseCase.getStationList()
                loading.value = false
            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }

    fun updateShipInfo(shipInfo: Ship) {

        viewModelScope.launch {
            try {
                var data = spaceUseCase.updateShipInfo(shipInfo)
                updateShipInfoLiveData.postValue(data)

            } catch (exception: Exception) {
                error.postValue(exception)
            }
        }

    }

    fun getShipInfo() {


        viewModelScope.launch {
            try {
                shipInfoLiveData.value = spaceUseCase.getShipInfo()

            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }


    fun updateMission(station: Station) {

        viewModelScope.launch {
            try {
                updateMissionLiveData.value = spaceUseCase.updateMission(station)

            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }


}