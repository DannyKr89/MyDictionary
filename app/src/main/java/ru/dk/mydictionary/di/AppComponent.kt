package ru.dk.mydictionary.di

import dagger.Component
import ru.dk.mydictionary.ui.list.SearchListViewModel
import javax.inject.Singleton


@Component(
    modules = [RepositoryModule::class,
        ViewModelModule::class]
)

@Singleton
interface AppComponent {

    fun inject(fragment: SearchListViewModel)
    val factory: ViewModelFactory
}