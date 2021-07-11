package net.plantix.demo.data.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.plantix.demo.data.model.ModelUserName

@Dao
interface UsernameDao {
    @Query("SELECT * FROM TableUserName")
    suspend fun getAllUserName() : List<ModelUserName>

    @Query("SELECT count(*) FROM TableUserName")
    suspend fun getUserCount() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUserName(countryList: List<ModelUserName>)

    @Query("DELETE FROM TableUserName")
    suspend fun deleteAllUserName()
}