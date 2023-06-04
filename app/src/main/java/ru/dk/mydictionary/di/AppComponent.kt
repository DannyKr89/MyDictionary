package ru.dk.mydictionary.di

import dagger.Component
import ru.dk.mydictionary.ui.list.SearchListFragment
import ru.dk.mydictionary.ui.list.ViewModelFactory
import javax.inject.Singleton


@Component(
    modules = [RepositoryModule::class,
        ViewModelModule::class]
)

@Singleton
interface AppComponent {

    fun inject(fragment: SearchListFragment)
    fun factory(): ViewModelFactory
}