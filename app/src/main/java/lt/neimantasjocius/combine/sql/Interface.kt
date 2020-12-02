package lt.neimantasjocius.combine.sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lt.neimantasjocius.combine.data.Image

@Dao
interface Interface {

    @Query("SELECT * FROM image_table ORDER BY id ASC")
    fun getImages(): Flow<List<Image>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: Image)

    @Query("DELETE FROM image_table")
    suspend fun deleteAll()
}