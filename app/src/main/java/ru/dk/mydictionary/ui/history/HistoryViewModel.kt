package ru.dk.mydictionary.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.dk.mydictionary.domain.WordListRepo
import ru.dk.mydictionary.data.state.AppState

class HistoryViewModel(
    private val repository: WordListRepo,
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val scope: CoroutineScope,
    private var job: Job? = null
) : ViewModel() {
    init {
        requestData()
    }

    fun getLiveData() = liveData

    fun requestData() {
        job = null

        job = scope.launch {
            repository.getDataAsync("")
                .catch {
                    liveData.postValue(AppState.Error(it))
                }
                .collect() {
                    liveData.postValue(AppState.Success(it.reversed()))
                }
        }
    }

    fun onClear() {
        job?.cancel()
    }

}