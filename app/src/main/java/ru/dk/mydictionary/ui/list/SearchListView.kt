package ru.dk.mydictionary.ui.list

import ru.dk.mydictionary.data.state.AppState

interface SearchListView {
    fun renderData(appState: AppState)
}