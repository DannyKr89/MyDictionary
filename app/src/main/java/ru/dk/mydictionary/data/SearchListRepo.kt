package ru.dk.mydictionary.data

import kotlinx.coroutines.flow.Flow
import ru.dk.mydictionary.data.model.DictionaryModel

interface SearchListRepo {
    suspend fun getDataAsync(word: String): Flow<List<DictionaryModel>>
}