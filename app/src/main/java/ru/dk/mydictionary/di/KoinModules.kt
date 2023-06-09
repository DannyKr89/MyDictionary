package ru.dk.mydictionary.di

import androidx.room.Room
import com.example.api.retrofit.SearchListApi
import com.example.history.room.HistoryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.HistoryListRepoImpl
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.domain.WordListRepo
import ru.dk.mydictionary.ui.adapters.ItemListAdapter
import ru.dk.mydictionary.ui.history.HistoryFragment
import ru.dk.mydictionary.ui.history.HistoryViewModel
import ru.dk.mydictionary.ui.list.SearchListViewModel
import ru.dk.mydictionary.utils.BlurEffect
import ru.dk.mydictionary.utils.OnlineLiveData
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory

val searchModule = module {

    viewModel {
        SearchListViewModel(
            repository = get(named("search")),
            scope = get(),
            db = get()
        )
    }

    single<WordListRepo>(named("search")) { SearchListRepoImpl(api = get()) }

}


val appModule = module {

    single { OnlineLiveData(get()) }
    single { BlurEffect() }

    factory<CoroutineScope> { CoroutineScope(Dispatchers.IO) }

    single<SearchListApi> { get<Retrofit>().create(SearchListApi::class.java) }

    single<String>(named("baseUrl")) { "https://dictionary.skyeng.ru/" }

    single<Retrofit> {
        Retrofit.Builder().baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
    }

    single {
        Room.databaseBuilder(
            get(),
            HistoryDatabase::class.java,
            "historyDB"
        ).build()
    }
}

val historyModule = module {

    single<WordListRepo>(named("history")) { HistoryListRepoImpl(db = get()) }


    scope<HistoryFragment> {
        scoped {
            HistoryViewModel(
                repository = get(named("history")),
                scope = get()
            )
        }
        scoped { ItemListAdapter() }
    }
}