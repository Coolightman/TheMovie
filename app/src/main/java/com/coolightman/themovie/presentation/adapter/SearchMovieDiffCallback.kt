package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.MovieSearch

class SearchMovieDiffCallback : DiffUtil.ItemCallback<MovieSearch>() {
    override fun areItemsTheSame(oldItem: MovieSearch, newItem: MovieSearch): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: MovieSearch, newItem: MovieSearch): Boolean {
        return oldItem == newItem
    }
}