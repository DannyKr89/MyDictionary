package ru.dk.mydictionary

import android.app.Application
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.presenters.SearchListPresenter
import ru.dk.mydictionary.presenters.SearchListPresenterImpl

class App : Application() {

    private val repository: SearchListRepo by lazy {
        SearchListRepoImpl()
    }

    val presenter: SearchListPresenter by lazy {
        SearchListPresenterImpl(repository)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        internal lateinit var instance: App
            private set
    }

}