package com.example.api.retrofit.model


import com.google.gson.annotations.SerializedName


data class Meaning(
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("soundUrl")
    val soundUrl: String?,
    @SerializedName("transcription")
    val transcription: String?,
    @SerializedName("translation")
    val translation: Translation?
)