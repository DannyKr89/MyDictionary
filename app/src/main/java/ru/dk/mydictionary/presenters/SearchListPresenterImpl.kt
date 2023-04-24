package ru.dk.mydictionary.presenters

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.ui.list.SearchListView

class SearchListPresenterImpl(private val repository: SearchListRepo) : SearchListPresenter {

    private var currentView: SearchListView? = null
    private val compositeDisposable = CompositeDisposable()

    override fun attach(searchView: SearchListView) {
        currentView = searchView
    }

    override fun detach() {
        currentView = null
        compositeDisposable.clear()
    }

    override fun requestData(word: String) {
        compositeDisposable.add(
            repository.getData(word).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { currentView?.renderData(AppState.Loading) }
                .subscribeBy(
                    onError = {
                        currentView?.renderData(AppState.Error(it))
                    },
                    onSuccess = {
                        currentView?.renderData(AppState.Success(it))
                    }
                ))
    }
}