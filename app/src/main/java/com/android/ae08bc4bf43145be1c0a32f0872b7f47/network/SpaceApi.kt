package com.android.ae08bc4bf43145be1c0a32f0872b7f47.network

import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import retrofit2.http.GET

interface SpaceApi {

    @GET("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getStationList(): List<Station>
}