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
import com.coolightman.themovie.util.RatingColor.setRatingColor

class ShortMovieAdapter(
    private val clickListener: (ShortMovie) -> Unit
) : ListAdapter<ShortMovie, ShortMovieViewHolder>(ShortMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMovieViewHolder {
        val binding = ShortMovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortMovieViewHolder, position: Int) {
        val shortMovie = getItem(position)
        shortMovie?.let {
            with(holder.binding) {
                setImage(this, shortMovie)
                setRating(this, shortMovie)
                setFavorite(this, shortMovie)
                root.setOnClickListener { clickListener(shortMovie) }
            }
        }
    }

    private fun setImage(binding: ShortMovieItemBinding, shortMovie: ShortMovie) {
        Glide.with(binding.root.context)
            .load(shortMovie.posterPreview)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(binding.imgPreview)
    }

    private fun setRating(binding: ShortMovieItemBinding, shortMovie: ShortMovie) {
        val rating = shortMovie.rating
        if (rating != null) {
            binding.tvRating.text = rating
            setRatingColor(binding.tvRating, rating)
            binding.tvRating.visibility = VISIBLE
        } else {
            binding.tvRating.visibility = GONE
        }
    }

    private fun setFavorite(binding: ShortMovieItemBinding, shortMovie: ShortMovie) {
        if (shortMovie.isFavorite) binding.imgFavorite.visibility = VISIBLE
        else binding.imgFavorite.visibility = GONE
    }
}