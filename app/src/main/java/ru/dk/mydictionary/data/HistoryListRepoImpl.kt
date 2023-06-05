package ru.dk.mydictionary.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dk.mydictionary.data.model.Word
import ru.dk.mydictionary.data.room.HistoryDatabase
import ru.dk.mydictionary.domain.WordListRepo

class HistoryListRepoImpl(private val db: HistoryDatabase) : WordListRepo {

    override suspend fun getDataAsync(word: String): Flow<List<Word>> = flow {
        emit(db.historyDao().getAll().map { convertHistoryToWord(it) })
    }
}
