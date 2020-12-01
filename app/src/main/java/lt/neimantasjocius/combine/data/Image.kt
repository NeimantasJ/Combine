package lt.neimantasjocius.combine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Image(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val path: String?
)
