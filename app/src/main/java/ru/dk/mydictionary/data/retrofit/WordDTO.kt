package ru.dk.mydictionary.data.retrofit


import com.google.gson.annotations.SerializedName


data class WordDTO(
    @SerializedName("meanings")
    val meanings: List<Meaning>?,
    @SerializedName("text")
    val text: String?
)