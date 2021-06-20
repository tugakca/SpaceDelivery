package com.android.ae08bc4bf43145be1c0a32f0872b7f47.repo

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.network.SpaceApi
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.room.SpaceDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpaceRepo
@Inject constructor(
        var ioDispatcher: CoroutineDispatcher,
        var spaceDao: SpaceDao,
        var api: SpaceApi,
) {


    suspend fun getStationList(): List<Station>? {

        var stationList: List<Station>? = null
        try {

            withContext(ioDispatcher) {
                stationList = api.getStationList()
                val tempDbList = spaceDao.getStationFromDb()
                if (tempDbList.isNullOrEmpty())
                    stationList?.let {
                        spaceDao.insert(*it.toTypedArray())
                    }
                else {
                    val firstListObjectIds = tempDbList.map { it.name }.toSet()
                    var filteredList: List<Station>? = null
                    stationList?.let { list ->
                        filteredList = list.filter { !firstListObjectIds.contains(it.name) }
                    }
                    filteredList.let {
                        spaceDao.insert(* it!!.toTypedArray())
                    }
                }
                stationList = spaceDao.getStationFromDb()
            }

        } catch (e: Exception) {
            throw  e
        }
        return stationList
    }


    suspend fun updateMission(station: Station): Station? {
        var stationItem: Station? = null
        withContext(ioDispatcher) {
            try {
                station.let {
                    it.isDone = true
                    spaceDao.updateStationItem(it)
                }

            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        station?.let {
            stationItem = spaceDao.getStationItem(it.spaceUuid!!)
        }
        return stationItem
    }


    suspend fun saveShipInfo(ship: Ship): Ship? {
        var shipInfo: Ship? = null
        withContext(ioDispatcher) {
            try {
                spaceDao.insertShip(ship)
            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        shipInfo = spaceDao.getShipInfoFromDb()
        return shipInfo
    }
}