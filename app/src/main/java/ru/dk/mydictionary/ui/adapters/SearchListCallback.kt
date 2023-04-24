package ru.dk.mydictionary.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.dk.mydictionary.data.model.DictionaryModel

class SearchListCallback : DiffUtil.ItemCallback<DictionaryModel>() {
    override fun areItemsTheSame(oldItem: DictionaryModel, newItem: DictionaryModel): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: DictionaryModel, newItem: DictionaryModel): Boolean {
        return oldItem == newItem
    }

}