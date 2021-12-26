package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.Movie

class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}