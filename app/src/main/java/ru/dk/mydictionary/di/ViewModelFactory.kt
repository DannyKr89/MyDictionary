package ru.dk.mydictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.core.Scheduler
import ru.dk.mydictionary.data.SearchListRepo

class ViewModelFactory(private val searchListRepo: SearchListRepo, private val uiScheduler: Scheduler) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SearchListRepo::class.java, Scheduler::class.java).newInstance(searchListRepo,uiScheduler)
    }
}