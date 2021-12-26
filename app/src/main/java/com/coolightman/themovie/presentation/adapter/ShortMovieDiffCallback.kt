package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.ShortMovie

class ShortMovieDiffCallback : DiffUtil.ItemCallback<ShortMovie>() {
    override fun areItemsTheSame(oldItem: ShortMovie, newItem: ShortMovie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: ShortMovie, newItem: ShortMovie): Boolean {
        return oldItem == newItem
    }
}