package ru.dk.mydictionary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.dk.mydictionary.di.repository
import ru.dk.mydictionary.di.room
import ru.dk.mydictionary.di.viewModel

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(repository, viewModel, room))
        }

    }

    companion object {
        internal lateinit var instance: App
            private set
    }

}