package ru.dk.mydictionary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.databinding.ListItemBinding

class SearchListAdapter :
    ListAdapter<DictionaryModel, SearchListAdapter.SearchListViewHolder>(SearchListCallback()) {

    inner class SearchListViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dictionaryModel: DictionaryModel) {
            with(binding) {
                titleListItem.text = dictionaryModel.text
                descriptionListItem.text = dictionaryModel.meanings?.first()?.translation?.text
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