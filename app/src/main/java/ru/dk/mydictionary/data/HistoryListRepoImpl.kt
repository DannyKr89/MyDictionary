package ru.dk.mydictionary.data

import com.example.history.room.HistoryDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dk.mydictionary.data.model.Word
import ru.dk.mydictionary.domain.WordListRepo
import ru.dk.mydictionary.utils.convertHistoryToWord

class HistoryListRepoImpl(private val db: HistoryDatabase) : WordListRepo {

    override suspend fun getDataAsync(word: String): Flow<List<Word>> = flow {
        emit(db.historyDao().getAll().map { convertHistoryToWord(it) })
    }
}
