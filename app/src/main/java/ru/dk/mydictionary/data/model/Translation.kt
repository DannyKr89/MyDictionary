package ru.dk.mydictionary.data.model


import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("text")
    val text: String?
)