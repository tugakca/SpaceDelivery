package com.android.ae08bc4bf43145be1c0a32f0872b7f47.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "station")
data class Station(
        @SerializedName("name")
        @ColumnInfo(name = "name")
        var name: String,
        @SerializedName("coordinateX")
        @ColumnInfo(name = "coordinateX")
        var coordinateX: Double,
        @SerializedName("coordinateY")
        @ColumnInfo(name = "coordinateY")
        var coordinateY: Double,
        @SerializedName("capacity")
        @ColumnInfo(name = "capacity")
        var capacity: Int,
        @SerializedName("stock")
        @ColumnInfo(name = "stock")
        var stock: Int,
        @SerializedName("need")
        @ColumnInfo(name = "need")
        var need: Int
) {
    @PrimaryKey(autoGenerate = true)
    var spaceUuid: Int? = null
    var isFavorite = false
    var isDone: Boolean? = false
    var eus: Double? = null
}