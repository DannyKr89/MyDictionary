package ru.dk.mydictionary.data.retrofit


import com.google.gson.annotations.SerializedName


data class Translation(
    @SerializedName("text")
    val text: String?
)