package com.lucascosta.catsanddogs.repository.api.service

import com.lucascosta.catsanddogs.repository.api.model.DogEntity
import retrofit2.Call
import retrofit2.http.GET

interface DogService {
    @GET("/api/facts")
    fun getNewFact() : Call<DogEntity>
}