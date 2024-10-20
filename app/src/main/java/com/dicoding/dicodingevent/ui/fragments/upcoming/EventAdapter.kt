package com.dicoding.dicodingevent.ui.fragments.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.ItemEventsBinding

class EventAdapter(private val onClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(private val binding: ItemEventsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onClick: (ListEventsItem) -> Unit) {
            binding.tvEventName.text = event.name
            binding.tvEventSummary.text = event.summary
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgEventPhoto)

            // Set onClickListener for item
            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}