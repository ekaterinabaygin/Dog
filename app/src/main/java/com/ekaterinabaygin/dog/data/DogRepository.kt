package com.ekaterinabaygin.dog.data

import com.ekaterinabaygin.dog.remote.DogService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class DogRepository(private val api: DogService) {

    suspend fun retrieveRandomDog(): Result<Dog> = withContext(Dispatchers.IO) {
        runCatching { api.retrieveRandomDog() }.mapCatching {
            require(URL(it.imageUrl).openConnection() != null)
            Dog(imageUrl = it.imageUrl)
        }
    }
}