package com.ekaterinabaygin.dog.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekaterinabaygin.dog.data.Dog
import com.ekaterinabaygin.dog.dependencies.DependenciesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class DogImageState {
    data object Loading : DogImageState()
    data class Error(val message: String) : DogImageState()
    data class Data(val image: Dog) : DogImageState()
}

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val dogRepository = DependenciesProvider.provideDogRepository()


    private val _dogImageState = MutableLiveData<DogImageState>()
    val dogImageState: LiveData<DogImageState> get() = _dogImageState

    init {
        loadRandomDog()
    }

    fun loadRandomDog() = viewModelScope.launch(Dispatchers.IO) {
        _dogImageState.postValue(DogImageState.Loading)
        val resultState = dogRepository.retrieveRandomDog().fold(
            onSuccess = { DogImageState.Data(it) },
            onFailure = {
                Log.e(TAG, "Failed to load dog", it)
                DogImageState.Error(it.localizedMessage ?: "")
            }
        )
        _dogImageState.postValue(resultState)
    }
}


