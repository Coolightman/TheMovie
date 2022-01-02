package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.ShortMovieItemBinding
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.util.RatingColor.setRatingColor
import com.google.android.material.textview.MaterialTextView

class FavoriteMovieAdapter(
    private val clickListener: (Movie) -> Unit
) : ListAdapter<Movie, ShortMovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMovieViewHolder {
        val binding = ShortMovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortMovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            with(holder.binding) {
                setImage(this, movie)
                setRating(this, movie)
                root.setOnClickListener { clickListener(movie) }
            }
        }
    }

    private fun setImage(binding: ShortMovieItemBinding, movie: Movie) {
        Glide.with(binding.root.context)
            .load(movie.posterPreview)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(binding.imgPreview)
    }

    private fun setRating(binding: ShortMovieItemBinding, movie: Movie) {
        val rating = movie.rating
        rating?.let {
            val ratingTextView = binding.tvRating
            ratingTextView.text = it
            setRatingColor(ratingTextView, it)
            ratingTextView.visibility = VISIBLE
        }
    }
}