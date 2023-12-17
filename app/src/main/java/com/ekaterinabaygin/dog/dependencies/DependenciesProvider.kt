package com.ekaterinabaygin.dog.dependencies

import com.ekaterinabaygin.dog.data.DogRepository
import com.ekaterinabaygin.dog.remote.DogService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependenciesProvider {

    private const val BASE_URL = "https://dog.ceo/api/breeds/image/"

    fun provideDogRepository() = DogRepository(
        api = provideDogService()
    )

    private fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideDogService(): DogService = provideRetrofit().create(DogService::class.java)
}


