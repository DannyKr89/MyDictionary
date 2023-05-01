package ru.dk.mydictionary.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import ru.dk.mydictionary.data.SearchListRepo
import ru.dk.mydictionary.data.state.AppState

class SearchListViewModel(
    private val repository: SearchListRepo,
    private val liveData: MutableLiveData<AppState>,
) : ViewModel() {
    private var lastWord: String? = null

    fun getLiveData() = liveData

    fun requestData(word: String) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable: Throwable ->
            liveData.postValue(AppState.Error(throwable))
        }) {
            liveData.postValue(AppState.Loading)
            val result = repository.getDataAsync(word).await()
            liveData.postValue(AppState.Success(result))
        }
        lastWord = word
    }

    fun getLastRequest() {
        lastWord?.let { requestData(it) }
    }
}