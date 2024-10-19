package com.dicoding.dicodingevent.ui.fragments.finished

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.ItemEventsFinishedHomeBinding

class FinishedAdapter(private val onClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, FinishedAdapter.FinishedViewHolder>(DIFF_CALLBACK) {

    class FinishedViewHolder(private val binding: ItemEventsFinishedHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onClick: (ListEventsItem) -> Unit) {
            binding.tvEventName.text = event.name
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgMediaCover)

            // Set onClickListener for item
            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedViewHolder {
        val binding = ItemEventsFinishedHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinishedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinishedViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onClick)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}