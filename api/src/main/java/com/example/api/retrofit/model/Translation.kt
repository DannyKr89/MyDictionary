package com.example.api.retrofit.model


import com.google.gson.annotations.SerializedName


data class Translation(
    @SerializedName("text")
    val text: String?
)