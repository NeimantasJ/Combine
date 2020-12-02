package lt.neimantasjocius.combine.sql

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import lt.neimantasjocius.combine.data.Image

class ImageRepository(private val dao: Interface) {
    val allImages: Flow<List<Image>> = dao.getImages()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(image: Image) {
        dao.insert(image)
    }
}