package ru.dk.mydictionary.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.data.retrofit.SearchListApi

class SearchListRepoImpl(private val api: SearchListApi) :
    SearchListRepo {

    override suspend fun getDataAsync(word: String): Flow<List<DictionaryModel>> = flow {
        emit(api.getList(word).await())
    }
}