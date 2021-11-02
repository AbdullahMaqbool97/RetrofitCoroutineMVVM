package com.example.samplecoroutinetask.Service

import com.example.samplecoroutinetask.Interface.ItemsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitService {
    private val BASE_URL = "https://api.github.com/search/"

    fun getItemService(): ItemsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItemsApi::class.java)
    }
}