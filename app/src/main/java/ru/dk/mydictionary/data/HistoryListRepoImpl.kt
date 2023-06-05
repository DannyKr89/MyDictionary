package ru.dk.mydictionary.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.data.model.Meaning
import ru.dk.mydictionary.data.model.Translation
import ru.dk.mydictionary.data.room.HistoryDatabase
import ru.dk.mydictionary.data.room.HistoryWord

class HistoryListRepoImpl(private val db: HistoryDatabase) : WordListRepo {

    override suspend fun getDataAsync(word: String): Flow<List<DictionaryModel>> = flow {
        emit(convertHistoryToModel(db.historyDao().getAll()))
    }

    private fun convertHistoryToModel(list: List<HistoryWord>): List<DictionaryModel> {
        return list.map {
            DictionaryModel(
                text = it.word,
                meanings = listOf(
                    Meaning(
                        it.imageUrl,
                        "",
                        it.transcription,
                        Translation(it.translation)
                    )
                )
            )
        }
    }
}