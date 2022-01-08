package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.Video

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.url == oldItem.url
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}