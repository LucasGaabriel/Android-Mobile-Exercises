package com.lucascosta.catsanddogs.repository.api.model

import com.google.gson.annotations.SerializedName

class DogEntity {
    @SerializedName("facts")
    val facts = mutableListOf<String>()
}