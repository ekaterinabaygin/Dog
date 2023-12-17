package com.ekaterinabaygin.dog.remote

import com.google.gson.annotations.SerializedName

data class DogDto(
    @SerializedName("message")
    val imageUrl: String
)