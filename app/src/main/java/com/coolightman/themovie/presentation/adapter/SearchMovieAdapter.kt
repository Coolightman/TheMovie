package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.MovieSearchItemBinding
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.util.RatingColor.setRatingColor

class SearchMovieAdapter(
    private val clickListener: (MovieSearch) -> Unit
) : ListAdapter<MovieSearch, SearchMovieViewHolder>(SearchMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val binding = MovieSearchItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            with(holder.binding) {
                setImage(this, movie)
                setRating(this, movie)
                root.setOnClickListener { clickListener(movie) }
            }
        }
    }

    private fun setImage(binding: MovieSearchItemBinding, movie: MovieSearch) {
        Glide.with(binding.root.context)
            .load(movie.posterPreview)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(binding.imgPreview)
    }

    private fun setRating(binding: MovieSearchItemBinding, movie: MovieSearch) {
        val rating = movie.rating
        if (rating != null) {
            binding.tvRating.text = rating
            setRatingColor(binding.tvRating, rating)
            binding.tvRating.visibility = VISIBLE
        } else {
            binding.tvRating.visibility = GONE
        }
    }
}