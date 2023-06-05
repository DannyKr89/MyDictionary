package ru.dk.mydictionary.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.dk.mydictionary.data.convertHistoryToModel
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.data.room.HistoryDatabase
import ru.dk.mydictionary.data.room.HistoryWord
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.domain.WordListRepo

class SearchListViewModel(
    private val repository: WordListRepo,
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val scope: CoroutineScope,
    private var job: Job? = null,
    private val db: HistoryDatabase

) : ViewModel() {
    private var lastWord: String? = null

    fun getLiveData() = liveData

    fun requestData(word: String) {
        job = null
        lastWord = word

        job = scope.launch {
            liveData.postValue(AppState.Loading)
            if (isWordInHistory(word)) {
                val historyWord = db.historyDao().getWord(word)?.let {
                    convertHistoryToModel(it)
                }
                liveData.postValue(AppState.Success(listOf(historyWord!!)))
            } else {
                repository.getDataAsync(word)
                    .catch {
                        liveData.postValue(AppState.Error(it))
                    }.collect() {
                        liveData.postValue(AppState.Success(it))
                        if (it.isNotEmpty()) {
                            saveWordToHistory(it.first())
                        }
                    }
            }

        }
    }

    private suspend fun isWordInHistory(word: String): Boolean {
        if (db.historyDao().getWord(word) != null) {
            return true
        }
        return false
    }

    fun saveWordToHistory(dictionaryModel: DictionaryModel) {
        job = scope.launch {
            db.historyDao().insert(
                HistoryWord(
                    dictionaryModel.text!!,
                    dictionaryModel.meanings?.first()?.translation?.text,
                    dictionaryModel.meanings?.first()?.transcription,
                    dictionaryModel.meanings?.first()?.imageUrl,
                )
            )
        }

    }

    fun getLastRequest() {
        lastWord?.let { requestData(it) }
    }

    fun onClear() {
        job?.cancel()
    }
}