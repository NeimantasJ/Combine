package lt.neimantasjocius.combine.sql

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ImageDao {

    @Insert
    fun insert(image: Image) : Single<Long>

    @Delete
    fun delete(image: Image) : Completable

    @Query("SELECT * FROM image")
    fun getAllImages() : Single<List<Image>>

    @Update
    fun update(image: Image) : Completable
}