package com.ekaterinabaygin.dog.remote

import retrofit2.http.GET

interface DogService {

    @GET("random")
    suspend fun retrieveRandomDog(): DogDto
}
