package ru.dk.mydictionary

import android.app.Application
import ru.dk.mydictionary.di.AppComponent
import ru.dk.mydictionary.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        internal lateinit var instance: App
            private set
    }

}