package ru.dk.mydictionary.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.dk.mydictionary.data.model.Word

class ItemListCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }

}