package com.example.samplecoroutinetask.service

import com.example.samplecoroutinetask.interface_.Itemsapi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val BASE_URL = "https://api.github.com/search/"

    fun getItemService(): Itemsapi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Itemsapi::class.java)
    }
}