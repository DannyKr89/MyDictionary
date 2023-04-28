package ru.dk.mydictionary.di

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.SearchListRepoImpl
import ru.dk.mydictionary.data.retrofit.SearchListApi
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://dictionary.skyeng.ru/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): SearchListApi = retrofit.create(SearchListApi::class.java)

    @Provides
    @Singleton
    @Named("IO")
    fun provideIo(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideRepository(api: SearchListApi, @Named("IO") ioScheduler: Scheduler): SearchListRepo =
        SearchListRepoImpl(api, ioScheduler)
}
