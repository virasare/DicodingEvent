package com.dicoding.dicodingevent.ui.fragments.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.local.FavoriteEventEntity
import com.dicoding.dicodingevent.databinding.ItemFavoriteBinding

class FavoriteAdapter (private val onClick: (FavoriteEventEntity) -> Unit): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteEvent : List<FavoriteEventEntity> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(favoriteEvent: List<FavoriteEventEntity>) {
        this.favoriteEvent = favoriteEvent
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEventEntity, onClick: (FavoriteEventEntity) -> Unit) {
            binding.tvEventName.text = event.name
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgEventPhoto)

            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = favoriteEvent.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val event = favoriteEvent[position]
        holder.bind(event, onClick)
    }
}