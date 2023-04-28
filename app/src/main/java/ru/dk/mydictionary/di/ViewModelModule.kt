package ru.dk.mydictionary.di

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import ru.dk.mydictionary.data.SearchListRepo
import javax.inject.Named
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    @Named("UI")
    fun provideUi(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun provideFactory(repository: SearchListRepo, @Named("UI") uiScheduler: Scheduler) =
        ViewModelFactory(repository, uiScheduler)

}