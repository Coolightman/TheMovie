package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.PersonFilm

class PersonFilmDiffCallback : DiffUtil.ItemCallback<PersonFilm>() {
    override fun areItemsTheSame(oldItem: PersonFilm, newItem: PersonFilm): Boolean {
        return oldItem.movieId == oldItem.movieId
    }

    override fun areContentsTheSame(oldItem: PersonFilm, newItem: PersonFilm): Boolean {
        return oldItem == newItem
    }
}