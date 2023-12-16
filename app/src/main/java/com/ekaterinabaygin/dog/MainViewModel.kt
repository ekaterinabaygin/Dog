package com.ekaterinabaygin.dog

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _dogImageState = MutableLiveData<DogImageState>()
    val dogImageState: LiveData<DogImageState> get() = _dogImageState

    private val _dogImage = MutableLiveData<DogImage>()
    val dogImage: LiveData<DogImage> get() = _dogImage

    sealed class DogImageState {
        data object Loading : DogImageState()
        data class Error(val message: String) : DogImageState()
        data class Data(val image: DogImage) : DogImageState()
    }

    init {
        loadRandomDogImage()
    }

    fun loadRandomDogImage() {
        viewModelScope.launch(Dispatchers.IO) {
            _dogImageState.postValue(DogImageState.Loading)

            try {
                val dog = ApiFactory.apiService.loadDogImage()

                _dogImageState.postValue(DogImageState.Data(dog))

                _dogImage.postValue(dog)
            } catch (e: Exception) {
                val errorMessage = "Failed to load dog image: ${e.message}"
                Log.e("MainViewModel", errorMessage)
                _dogImageState.postValue(DogImageState.Error(errorMessage))
            }
        }
    }
}


