package lt.neimantasjocius.combine.sql

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val path: String?
)
