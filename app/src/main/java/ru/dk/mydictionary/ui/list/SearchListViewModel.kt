package ru.dk.mydictionary.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.dk.mydictionary.data.WordListRepo
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.data.room.HistoryDatabase
import ru.dk.mydictionary.data.room.HistoryWord
import ru.dk.mydictionary.data.state.AppState

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
            repository.getDataAsync(word)
                .catch {
                    liveData.postValue(AppState.Error(it))
                }
                .collect() {
                    liveData.postValue(AppState.Success(it))
                    if (it.isNotEmpty()) {
                        db.historyDao().insert(saveWordToHistory(it))
                    }
                }
        }
    }

    private fun saveWordToHistory(list: List<DictionaryModel>): HistoryWord {
        return HistoryWord(
            list.first().text!!,
            list.first().meanings!!.first().translation?.text,
            list.first().meanings!!.first().transcription,
            list.first().meanings!!.first().imageUrl,
        )
    }

    fun getLastRequest() {
        lastWord?.let { requestData(it) }
    }

    fun onClear() {
        job?.cancel()
    }
}