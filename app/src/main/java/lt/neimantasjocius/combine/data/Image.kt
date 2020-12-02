package lt.neimantasjocius.combine.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class Image(
    @ColumnInfo(name = "path") val path: String?
)
