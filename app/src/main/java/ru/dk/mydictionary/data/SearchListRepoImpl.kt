package ru.dk.mydictionary.data

import io.reactivex.rxjava3.core.Scheduler
import ru.dk.mydictionary.data.retrofit.SearchListApi

class SearchListRepoImpl(private val api: SearchListApi, private val ioSchedulers: Scheduler) :
    SearchListRepo {

    override fun getData(word: String) = api.getList(word).subscribeOn(ioSchedulers)
}