package ru.dk.mydictionary

import android.app.Application
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.SearchListRepoImpl

class App : Application() {

    val repository: SearchListRepo by lazy {
        SearchListRepoImpl()
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