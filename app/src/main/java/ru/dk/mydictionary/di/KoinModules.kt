package ru.dk.mydictionary.di

import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<SearchListApi> { get<Retrofit>().create(SearchListApi::class.java) }

    single<SearchListRepo> { SearchListRepoImpl(api = get()) }
}


val viewModel = module {

    single<MutableLiveData<AppState>> { MutableLiveData() }


    viewModel {
        SearchListViewModel(
            repository = get(),
            liveData = get()
        )
    }
}