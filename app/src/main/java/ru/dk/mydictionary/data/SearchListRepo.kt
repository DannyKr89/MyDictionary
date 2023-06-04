package ru.dk.mydictionary.data

import ru.dk.mydictionary.data.model.DictionaryModel

interface SearchListRepo {
    suspend fun getDataAsync(word: String): List<DictionaryModel>
}