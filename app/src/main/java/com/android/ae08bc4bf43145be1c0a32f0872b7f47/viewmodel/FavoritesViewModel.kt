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
class FavoritesViewModel
@Inject constructor(var spaceUseCase: SpaceUseCase) : ViewModel() {

    val favStationLiveData: MutableLiveData<ArrayList<Station>> by lazy { MutableLiveData<ArrayList<Station>>() }
    val updateFav: MutableLiveData<Station> by lazy { MutableLiveData<Station>() }
    val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val error: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }


    fun updateFav(addToFav: Boolean, station: Station) {
        loading.value = true
        viewModelScope.launch {
            try {
                updateFav.value = spaceUseCase.favoriteOperation(addToFav, station)
                loading.value = false
            } catch (exception: Exception) {
                error.value = exception
            }
        }

    }

    fun getFavList() {
        loading.value = true
        viewModelScope.launch {
            try {
                favStationLiveData.value = spaceUseCase.getFavList()
                loading.value = false
            } catch (exception: Exception) {
                error.value = exception
            }
        }
    }
}