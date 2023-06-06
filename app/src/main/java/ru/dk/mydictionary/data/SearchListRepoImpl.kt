package ru.dk.mydictionary.data

import com.example.api.retrofit.SearchListApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import ru.dk.mydictionary.data.model.Word
import ru.dk.mydictionary.domain.WordListRepo
import ru.dk.mydictionary.utils.convertDTOToModel

class SearchListRepoImpl(private val api: SearchListApi) :
    WordListRepo {

    override suspend fun getDataAsync(word: String): Flow<List<Word>> = flow {
        emit(api.getList(word).await().map { convertDTOToModel(it) })
    }
}