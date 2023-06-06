package ru.dk.mydictionary.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.dk.mydictionary.utils.convertHistoryToWord
import ru.dk.mydictionary.data.model.Word
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.domain.WordListRepo

class SearchListViewModel(
    private val repository: WordListRepo,
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val scope: CoroutineScope,
    private var job: Job? = null,
    private val db: com.example.history.room.HistoryDatabase

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
                    convertHistoryToWord(it)
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

    fun saveWordToHistory(word: Word) {
        job = scope.launch {
            db.historyDao().insert(
                com.example.history.room.HistoryWord(
                    word.word,
                    word.translation,
                    word.transcription,
                    word.imageUrl,
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