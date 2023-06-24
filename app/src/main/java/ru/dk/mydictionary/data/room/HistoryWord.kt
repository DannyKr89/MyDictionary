package ru.dk.mydictionary.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
data class HistoryWord(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "translation")
    val translation: String?,
    @ColumnInfo(name = "transcription")
    val transcription: String?,
    @ColumnInfo(name = "image")
    val imageUrl: String?,
)