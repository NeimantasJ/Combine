package lt.neimantasjocius.combine.sql

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import lt.neimantasjocius.combine.data.Image

class ImageViewModel(private val repository: ImageRepository) : ViewModel() {
    val allImages: LiveData<List<Image>> = repository.allImages.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(image: Image) = viewModelScope.launch {
        repository.insert(image)
    }
}

class ImageViewModelFactory(private val repository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}