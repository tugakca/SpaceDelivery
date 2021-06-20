
package  com.android.ae08bc4bf43145be1c0a32f0872b7f47.room
import androidx.room.*
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station


@Dao
interface SpaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg  station: Station): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShip(vararg  ship: Ship): List<Long>

    @Update
    public fun updateStationItem(station: Station)

    @Query("SELECT * FROM station ")
    suspend fun getStationFromDb(): List<Station>

    @Query("SELECT * FROM ship ")
    suspend fun getShipInfoFromDb(): Ship


    @Query("SELECT * FROM station WHERE spaceUuid = :uuid")
    suspend fun getStationItem(uuid:Int): Station

}