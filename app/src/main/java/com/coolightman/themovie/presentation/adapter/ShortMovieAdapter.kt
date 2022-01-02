package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.ShortMovieItemBinding
import com.coolightman.themovie.domain.entity.ShortMovie
import com.google.android.material.textview.MaterialTextView

class ShortMovieAdapter(
    private val clickListener: (ShortMovie) -> Unit
) : ListAdapter<ShortMovie, ShortMovieViewHolder>(ShortMovieDiffCallback()) {

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
                setFavorite(this, movie)
                root.setOnClickListener { clickListener(movie) }
            }
        }
    }

    private fun setImage(binding: ShortMovieItemBinding, movie: ShortMovie) {
        Glide.with(binding.root.context)
            .load(movie.posterPreview)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(binding.imgPreview)
    }

    private fun setRating(binding: ShortMovieItemBinding, movie: ShortMovie) {
        val rating = movie.rating
        if (rating != null) {
            binding.tvRating.text = rating
            setRatingColor(binding.tvRating, rating)
            binding.tvRating.visibility = VISIBLE
        } else {
            binding.tvRating.visibility = GONE
        }
    }

    private fun setRatingColor(view: MaterialTextView, rating: String) {
        when {
            rating.contains(Regex("^[7-9]")) -> view.setBackgroundResource(R.drawable.rounded_corner_green)
            rating.contains(Regex("^[56]")) -> view.setBackgroundResource(R.drawable.rounded_corner_grey)
            rating.contains(Regex("^[2-4]")) -> view.setBackgroundResource(R.drawable.rounded_corner_red)
        }
    }

    private fun setFavorite(binding: ShortMovieItemBinding, movie: ShortMovie) {
        if (movie.isFavorite) binding.imgFavorite.visibility = VISIBLE
        else binding.imgFavorite.visibility = GONE
    }
}