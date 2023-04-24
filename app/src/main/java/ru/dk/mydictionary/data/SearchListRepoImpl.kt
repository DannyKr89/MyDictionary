package ru.dk.mydictionary.data

import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.retrofit.SearchListApi

class SearchListRepoImpl : SearchListRepo {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dictionary.skyeng.ru/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(SearchListApi::class.java)

    override fun getData(word: String) = api.getList(word).subscribeOn(Schedulers.io())
}