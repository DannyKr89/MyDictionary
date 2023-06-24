package ru.dk.mydictionary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.databinding.ListItemBinding

class ItemListAdapter :
    ListAdapter<DictionaryModel, ItemListAdapter.SearchListViewHolder>(ItemListCallback()) {

    var listener: ((DictionaryModel) -> Unit)? = null

    inner class SearchListViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dictionaryModel: DictionaryModel) {
            with(binding) {
                root.setOnClickListener {
                    listener?.invoke(dictionaryModel)
                }
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