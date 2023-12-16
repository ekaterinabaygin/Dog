package com.ekaterinabaygin.dog

import retrofit2.http.GET

interface ApiService {

    @GET("random")
    suspend fun loadDogImage(): DogImage

}
