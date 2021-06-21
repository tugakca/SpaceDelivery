package com.android.ae08bc4bf43145be1c0a32f0872b7f47.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station


@Database(entities = [Station::class, Ship::class], version = 1)
abstract class SpaceDatabase : RoomDatabase() {
    abstract fun spaceDao(): SpaceDao

    companion object {
        val DATABASE_NAME: String = "space_db"
    }
}