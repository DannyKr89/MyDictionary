package ru.dk.mydictionary.data

import retrofit2.Call
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.data.retrofit.SearchListApi

class SearchListRepoImpl(private val api: SearchListApi) :
    SearchListRepo {

    override suspend fun getDataAsync(word: String): Call<List<DictionaryModel>> {
        return api.getList(word)
    }
}