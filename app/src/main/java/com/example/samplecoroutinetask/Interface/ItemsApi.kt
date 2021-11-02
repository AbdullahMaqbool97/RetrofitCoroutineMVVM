package com.example.samplecoroutinetask.Interface

import com.example.samplecoroutinetask.Model.itemModel
import retrofit2.Response
import retrofit2.http.GET

interface ItemsApi {
    @GET("repositories?q=abdullah")
    suspend fun getItems(): Response<itemModel>
}
