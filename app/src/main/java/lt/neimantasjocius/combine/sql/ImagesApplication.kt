package lt.neimantasjocius.combine.sql

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ImagesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ImageDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ImageRepository(database.dao()) }
}