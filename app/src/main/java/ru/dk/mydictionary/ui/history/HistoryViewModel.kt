package ru.dk.mydictionary.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dk.mydictionary.data.room.HistoryDatabase
import ru.dk.mydictionary.data.room.HistoryWord

class HistoryViewModel(
    private val liveData: MutableLiveData<List<HistoryWord>> = MutableLiveData(),
    private val db: HistoryDatabase
) : ViewModel() {

    fun getLiveData() = liveData

    fun requestData() {
        viewModelScope.launch {
            liveData.postValue(db.historyDao().getAll())
        }
    }

}