package com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.repo.SpaceRepo
import java.lang.Exception
import javax.inject.Inject

class SpaceUseCase
@Inject constructor(var spaceRepo: SpaceRepo) {


    suspend fun favoriteOperation(addToFav: Boolean, station: Station): Station {
        var stationItem: Station? = null
        try {
            stationItem = spaceRepo.favoriteOperation(addToFav, station)
        } catch (e: Exception) {
            throw e
        }
        return stationItem
    }


    suspend fun deleteDB(): Boolean {
        var success = false
        try {
            success = spaceRepo.deleteDB()
        } catch (e: Exception) {
            throw e
        }
        return success
    }


    suspend fun searchItem(value: String): ArrayList<Station> {
        var searchList: ArrayList<Station>? = null
        try {
            searchList = spaceRepo.searchItem(value)
        } catch (e: Exception) {
            throw e
        }

        return searchList!!
    }


    suspend fun getFavList(): ArrayList<Station>? {
        var stationList: List<Station>? = null
        try {
            stationList = spaceRepo.getFavList()
        } catch (e: Exception) {
            throw e
        }

        return stationList
    }

    suspend fun getStationList(): List<Station>? {
        var stationList: List<Station>? = null
        try {
            stationList = spaceRepo.getStationList()
        } catch (e: Exception) {
            throw e
        }

        return stationList
    }

    suspend fun getShipInfo(): Ship {
        var shipItem: Ship? = null
        try {
            shipItem = spaceRepo.getShipInfo()
        } catch (e: Exception) {
            throw e
        }

        return shipItem!!
    }

    suspend fun updateShipInfo(shipInfo: Ship): Ship {
        var shipItem: Ship? = null
        try {
            shipItem = spaceRepo.updateShipInfo(shipInfo)
        } catch (e: Exception) {
            throw e
        }

        return shipItem!!
    }


    suspend fun updateMission(station: Station): Station? {
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