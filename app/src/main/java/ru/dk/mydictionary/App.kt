package ru.dk.mydictionary

import android.app.Application
import ru.dk.mydictionary.di.AppComponent
import ru.dk.mydictionary.di.DaggerAppComponent

class App : Application() {

//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://dictionary.skyeng.ru/")
//        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

//    private val api: SearchListApi by lazy { retrofit.create(SearchListApi::class.java) }
//    private val ioScheduler: Scheduler by lazy { Schedulers.io() }
//    private val uiScheduler: Scheduler by lazy { AndroidSchedulers.mainThread() }
//    private val repository: SearchListRepo by lazy { SearchListRepoImpl(api, ioScheduler) }

//    val viewModelFactory: ViewModelFactory by lazy {
//        ViewModelFactory(repository, uiScheduler)
//    }

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        internal lateinit var instance: App
            private set
    }

}