package ru.dk.mydictionary.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meaning(
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("soundUrl")
    val soundUrl: String?,
    @SerializedName("transcription")
    val transcription: String?,
    @SerializedName("translation")
    val translation: Translation?
) : Parcelable