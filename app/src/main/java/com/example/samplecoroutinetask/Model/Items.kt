package com.example.samplecoroutinetask.Model

import com.google.gson.annotations.SerializedName
import kotlin.collections.HashMap

data class Items(
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val node_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val full_name: String,
    @SerializedName("private") val private: Boolean,
    @SerializedName("owner") val owner: HashMap<String, Any>
)