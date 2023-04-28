package ru.dk.mydictionary.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.core.Scheduler
import ru.dk.mydictionary.data.SearchListRepo
import javax.inject.Singleton

@Singleton
class ViewModelFactory(
    private val searchListRepo: SearchListRepo,
    private val uiScheduler: Scheduler
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchListViewModel(searchListRepo, uiScheduler) as T
    }
}