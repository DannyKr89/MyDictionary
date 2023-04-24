package ru.dk.mydictionary.presenters

import ru.dk.mydictionary.ui.list.SearchListView

interface SearchListPresenter {
    fun attach(searchView: SearchListView)
    fun detach()
    fun requestData(word: String)
}