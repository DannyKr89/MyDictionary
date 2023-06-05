package ru.dk.mydictionary.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import ru.dk.mydictionary.data.model.Word
import ru.dk.mydictionary.domain.WordListRepo

class SearchListRepoImpl(private val api: com.example.api.retrofit.SearchListApi) :
    WordListRepo {

    override suspend fun getDataAsync(word: String): Flow<List<Word>> = flow {
        emit(api.getList(word).await().map { convertDTOToModel(it) })
    }
}