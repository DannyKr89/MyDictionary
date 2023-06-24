package ru.dk.mydictionary.di

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.HistoryListRepoImpl
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.domain.WordListRepo
import ru.dk.mydictionary.ui.history.HistoryViewModel
import ru.dk.mydictionary.ui.list.SearchListViewModel
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory

val repository = module {
    single<String>(named("baseUrl")) { "https://dictionary.skyeng.ru/" }

    single<Retrofit> {
        Retrofit.Builder().baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
    }

    single<com.example.api.retrofit.SearchListApi> { get<Retrofit>().create(com.example.api.retrofit.SearchListApi::class.java) }

    single<WordListRepo>(named("search")) { SearchListRepoImpl(api = get()) }

    single<WordListRepo>(named("history")) { HistoryListRepoImpl(db = get()) }

    single<CoroutineScope> { CoroutineScope(Dispatchers.IO) }
}


val viewModel = module {

    single<MutableLiveData<AppState>> { MutableLiveData() }

    viewModel {
        SearchListViewModel(
            repository = get(named("search")),
            scope = get(),
            db = get()
        )
    }

    viewModel {
        HistoryViewModel(
            repository = get(named("history")),
            scope = get()
        )
    }

}

val room = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            com.example.history.room.HistoryDatabase::class.java,
            "historyDB"
        ).build()
    }

}