package com.lucascosta.catsanddogs.repository.api.client

import com.lucascosta.catsanddogs.utils.Constants.API.BASE_CAT_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofitCat {
    companion object {
        private lateinit var INSTANCE: Retrofit
        private const val BASE_URL = BASE_CAT_URL

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()
            if (!Companion::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }

        fun <S> createService(className: Class<S>): S {
            return getRetrofitInstance().create(className)
        }
    }
}