package com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.repo.SpaceRepo
import java.lang.Exception
import javax.inject.Inject

class SpaceUseCase
@Inject constructor(var spaceRepo: SpaceRepo) {

    suspend fun getStationList(): List<Station>? {
        var stationList: List<Station>? = null
        try {
            stationList = spaceRepo.getStationList()
        } catch (e: Exception) {
            throw e
        }

        return stationList
    }


    suspend fun updateMission(station:Station): Station? {
        var stationItem: Station? = null
        try {
            stationItem = spaceRepo.updateMission(station)
        } catch (e: Exception) {
            throw e
        }

        return stationItem
    }


    suspend fun saveShipInfo(ship: Ship): Ship {
        var shipItem: Ship? = null
        try {
            shipItem = spaceRepo.saveShipInfo(ship)
        } catch (e: Exception) {
            throw e
        }

        return shipItem!!
    }
}