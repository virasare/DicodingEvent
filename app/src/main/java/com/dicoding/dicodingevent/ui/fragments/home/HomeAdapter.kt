package com.dicoding.dicodingevent.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.ItemEventsUpcomingHomeBinding

class HomeAdapter(private val onClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    class HomeViewHolder(private val binding: ItemEventsUpcomingHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onClick: (ListEventsItem) -> Unit) {
            binding.tvEventName.text = event.name
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgEventPhoto)

            // Set onClickListener for item
            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemEventsUpcomingHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
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