package ru.dk.mydictionary.data.state

import ru.dk.mydictionary.data.model.Word

sealed class AppState {
    data class Success(val list: List<Word>?) : AppState()
    data class Error(val throwable: Throwable) : AppState()
    object Loading : AppState()
}
