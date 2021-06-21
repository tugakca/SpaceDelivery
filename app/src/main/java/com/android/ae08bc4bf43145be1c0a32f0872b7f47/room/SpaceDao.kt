package  com.android.ae08bc4bf43145be1c0a32f0872b7f47.room

import androidx.room.*
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import retrofit2.http.DELETE


@Dao
interface SpaceDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg station: Station): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShip(vararg ship: Ship): List<Long>

    @Query("DELETE  FROM station ")
    suspend fun deleteDBStation()

    @Query("DELETE  FROM ship ")
    suspend fun deleteDBShip()

    @Update
    public fun updateStationItem(station: Station)

    @Update
    public fun updateShipItem(ship: Ship)

    @Query("SELECT * FROM station ")
    suspend fun getStationFromDb(): List<Station>

    @Query("SELECT * FROM ship ")
    suspend fun getShipInfoFromDb(): Ship

    @Query("SELECT * FROM station WHERE isFavorite = :value AND spaceUuid = :id ")
    suspend fun getFavList(value: Boolean, id: Int): Station


    @Query("SELECT * FROM station WHERE spaceUuid = :uuid")
    suspend fun getStationItem(uuid: Int): Station

}