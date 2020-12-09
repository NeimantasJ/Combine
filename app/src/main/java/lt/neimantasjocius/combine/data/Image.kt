package lt.neimantasjocius.combine.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "path") val path: String?
)
