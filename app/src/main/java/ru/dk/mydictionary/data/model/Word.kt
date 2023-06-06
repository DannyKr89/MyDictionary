package ru.dk.mydictionary.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val word: String,
    val translation: String = "",
    val transcription: String = "",
    val imageUrl: String = "",
) : Parcelable