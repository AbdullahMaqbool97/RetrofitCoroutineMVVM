package com.example.samplecoroutinetask.Interface

import com.example.samplecoroutinetask.Model.itemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Itemsapi {
    @GET("repositories")/*?q=abdullah*/
    suspend fun getItems(@Query("q") name: String): Response<itemModel>
}
