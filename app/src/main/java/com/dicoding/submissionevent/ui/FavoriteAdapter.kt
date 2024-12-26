package com.dicoding.submissionevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionevent.data.response.ListEventsItem
import com.dicoding.submissionevent.databinding.ItemMainBinding

class FavoriteAdapter(private val favoriteClickListener: OnFavoriteClickListener) :
    ListAdapter<ListEventsItem, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

        holder.itemView.setOnClickListener {
            favoriteClickListener.onFavoriteClick(event)
        }
        holder.itemView.setOnLongClickListener {
            favoriteClickListener.onFavoriteLongClick(event)
            true
        }
    }

    class FavoriteViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
            binding.tvTitle.text = event.name
            binding.tvDate.text = "${event.beginTime} - ${event.endTime}"

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imageView)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface OnFavoriteClickListener {
    fun onFavoriteClick(event: ListEventsItem)
    fun onFavoriteLongClick(event: ListEventsItem)
}
