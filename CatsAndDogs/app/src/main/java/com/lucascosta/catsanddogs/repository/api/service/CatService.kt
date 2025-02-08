package com.lucascosta.catsanddogs.repository.api.service

import com.lucascosta.catsanddogs.repository.api.model.CatEntity
import retrofit2.Call
import retrofit2.http.GET

interface CatService {
    @GET("/fact")
    fun getNewFact() : Call<CatEntity>
}