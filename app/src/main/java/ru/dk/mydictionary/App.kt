package ru.dk.mydictionary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.dk.mydictionary.di.appModule
import ru.dk.mydictionary.di.historyModule
import ru.dk.mydictionary.di.searchModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, searchModule, historyModule))
        }

    }

    companion object {
        internal lateinit var instance: App
            private set
    }

}