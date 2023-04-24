package ru.dk.mydictionary.data.state

import ru.dk.mydictionary.data.model.DictionaryModel

sealed class AppState {
    data class Success(val list: List<DictionaryModel>?) : AppState()
    data class Error(val throwable: Throwable) : AppState()
    object Loading : AppState()
}
