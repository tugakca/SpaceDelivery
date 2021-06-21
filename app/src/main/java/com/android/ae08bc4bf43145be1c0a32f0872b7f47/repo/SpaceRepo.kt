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


    suspend fun searchItem(value: String): ArrayList<Station> {
        var allList: List<Station>? = null
        var searchList = arrayListOf<Station>()
        withContext(ioDispatcher) {
            try {
                allList = spaceDao.getStationFromDb()

                allList!!.forEach {
                    if (it.name.contains(value, ignoreCase = true)) {
                        searchList.add(it)

                    }
                }

            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        return searchList
    }


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


    suspend fun getShipInfo(): Ship? {
        var shipInfo: Ship? = null
        withContext(ioDispatcher) {
            try {
                shipInfo = spaceDao.getShipInfoFromDb()
            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        return shipInfo
    }


    suspend fun updateShipInfo(shipInfo: Ship): Ship {
        var ship: Ship? = null
        withContext(ioDispatcher) {
            try {

                spaceDao.updateShipItem(shipInfo)

            } catch (e: java.lang.Exception) {
                throw e
            }
        }

        ship = spaceDao.getShipInfoFromDb()
        return ship
    }

    suspend fun getFavList(): ArrayList<Station> {
        var stationList: List<Station>? = null
        var favList: ArrayList<Station> = arrayListOf()
        withContext(ioDispatcher) {
            try {
                stationList = spaceDao.getStationFromDb()

                stationList!!.forEach {
                    if (it.isFavorite!!) {
                        favList.add(it)
                    }
                }
            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        return favList
    }


    suspend fun deleteDB(): Boolean {
        var success = false

        withContext(ioDispatcher) {
            try {

                var stationList = spaceDao.getStationFromDb()
                var shipItem = spaceDao.getShipInfoFromDb()

                if (!stationList.isNullOrEmpty() && shipItem != null) {
                    spaceDao.deleteDBShip()
                    spaceDao.deleteDBStation()
                    success = true
                }
            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        return success
    }

    suspend fun favoriteOperation(addToFav: Boolean, station: Station): Station {
        var list: Station? = null
        withContext(ioDispatcher) {
            try {
                station.isFavorite = addToFav
                spaceDao.updateStationItem(station)
            } catch (e: java.lang.Exception) {
                throw e
            }
        }
        list = spaceDao.getFavList(addToFav, station.spaceUuid!!)
        return list
    }
}