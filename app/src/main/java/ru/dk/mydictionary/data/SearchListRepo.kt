package ru.dk.mydictionary.data

import io.reactivex.rxjava3.core.Single
import ru.dk.mydictionary.data.model.DictionaryModel

interface SearchListRepo {
    fun getData(word: String): Single<List<DictionaryModel>>
}