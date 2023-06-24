package ru.dk.mydictionary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dk.mydictionary.data.model.Word
import ru.dk.mydictionary.databinding.ListItemBinding

class ItemListAdapter :
    ListAdapter<Word, ItemListAdapter.SearchListViewHolder>(ItemListCallback()) {

    var listener: ((Word) -> Unit)? = null

    inner class SearchListViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            with(binding) {
                root.setOnClickListener {
                    listener?.invoke(word)
                }
                titleListItem.text = word.word
                descriptionListItem.text = word.translation
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}