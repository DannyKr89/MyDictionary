package ru.dk.mydictionary.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.state.AppState

class SearchListViewModel(
    private val repository: SearchListRepo,
    private val uiScheduler: Scheduler
) : ViewModel() {

    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getLiveData() = liveData

    fun requestData(word: String) {
        compositeDisposable.add(
            repository.getData(word).observeOn(uiScheduler)
                .doOnSubscribe { liveData.postValue(AppState.Loading) }
                .subscribeBy(
                    onError = {
                        liveData.postValue(AppState.Error(it))
                    },
                    onSuccess = {
                        liveData.postValue(AppState.Success(it))
                    }
                ))
    }

    fun onClear() {
        compositeDisposable.clear()
    }
}