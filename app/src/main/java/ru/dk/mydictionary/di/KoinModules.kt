package ru.dk.mydictionary.di

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.data.retrofit.SearchListApi
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.ui.list.SearchListViewModel

val repository = module {
    single<String>(named("baseUrl")) { "https://dictionary.skyeng.ru/" }

    single<Retrofit> {
        Retrofit.Builder().baseUrl(get<String>(named("baseUrl")))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<SearchListApi> { get<Retrofit>().create(SearchListApi::class.java) }

    single<Scheduler>(named("IO")) { Schedulers.io() }

    single<SearchListRepo> { SearchListRepoImpl(api = get(), ioSchedulers = get(named("IO"))) }
}

val viewModelFactory = module {

    single<Scheduler>(named("UI")) { AndroidSchedulers.mainThread() }
}

val viewModel = module {

    single<MutableLiveData<AppState>> { MutableLiveData() }

    single { CompositeDisposable() }

    viewModel {
        SearchListViewModel(
            repository = get(),
            uiScheduler = get(named("UI")),
            liveData = get(),
            compositeDisposable = get()
        )
    }
}