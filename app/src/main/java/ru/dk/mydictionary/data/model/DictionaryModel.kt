package ru.dk.mydictionary.data.model


import com.google.gson.annotations.SerializedName

data class DictionaryModel(
    @SerializedName("meanings")
    val meanings: List<Meaning>?,
    @SerializedName("text")
    val text: String?
)