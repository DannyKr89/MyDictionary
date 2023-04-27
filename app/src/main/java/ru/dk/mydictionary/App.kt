package ru.dk.mydictionary

import android.app.Application
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.data.retrofit.SearchListApi
import ru.dk.mydictionary.di.ViewModelFactory

class App : Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dictionary.skyeng.ru/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: SearchListApi by lazy { retrofit.create(SearchListApi::class.java) }
    private val ioScheduler: Scheduler by lazy { Schedulers.io() }
    private val uiScheduler: Scheduler by lazy { AndroidSchedulers.mainThread() }
    private val repository: SearchListRepo by lazy { SearchListRepoImpl(api, ioScheduler) }

    val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(repository, uiScheduler)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        internal lateinit var instance: App
            private set
    }

}