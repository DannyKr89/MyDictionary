package ru.dk.mydictionary.domain

import kotlinx.coroutines.flow.Flow
import ru.dk.mydictionary.data.model.DictionaryModel

interface WordListRepo {
    suspend fun getDataAsync(word: String): Flow<List<DictionaryModel>>
}