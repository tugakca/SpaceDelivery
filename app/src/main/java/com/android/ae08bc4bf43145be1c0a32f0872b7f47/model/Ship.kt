package com.android.ae08bc4bf43145be1c0a32f0872b7f47.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ship")
data class Ship(
        @ColumnInfo(name = "name")
        var name: String?,
        @ColumnInfo(name = "durability")
        var durability: Int?,
        @ColumnInfo(name = "capasity")
        var capasity: Int?,
        @ColumnInfo(name = "speed")
        var speed: Int?,
        @ColumnInfo(name = "currentLocation")
        var currentLocation: String?,
        @ColumnInfo(name = "dirX")
        var dirX: Double?,
        @ColumnInfo(name = "dirY")
        var dirY: Double?,
        @ColumnInfo(name = "currentUGS")
        var currentUGS: Double?,
        @ColumnInfo(name = "currentEUS")
        var currentEUS: Double?,
        @ColumnInfo(name = "currentDS")
        var currentDS: Double?,
        @ColumnInfo(name = "damage")
        var damage: Int?

) {
    @PrimaryKey(autoGenerate = true)
    var shipUuid: Int? = null
    var allMissionsDone = false
}