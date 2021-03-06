package com.example.samplecoroutinetask.model

import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val node_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val full_name: String,
    @SerializedName("private") val private: Boolean,
    @SerializedName("owner") val owner: HashMap<String, Any>
)