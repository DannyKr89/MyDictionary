package ru.dk.mydictionary.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Translation(
    @SerializedName("text")
    val text: String?
) : Parcelable