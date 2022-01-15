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
                setNameEn(this, movie.nameEn)
                setNameRu(this, movie.nameRu)
                setReleaseDate(this, movie.releaseDate)
                setDuration(this, movie.duration)
                root.setOnClickListener { clickListener(movie) }
            }
        }
    }

    private fun setNameEn(binding: MovieSearchItemBinding, nameEn: String?) {
        if (nameEn != null) {
            binding.tvNameEn.text = nameEn
            binding.tvNameEn.visibility = VISIBLE
        } else {
            binding.tvNameEn.visibility = GONE
        }
    }

    private fun setNameRu(binding: MovieSearchItemBinding, nameRu: String?) {
        if (nameRu != null) {
            binding.tvNameRu.text = nameRu
            binding.tvNameRu.visibility = VISIBLE
        } else {
            binding.tvNameRu.visibility = GONE
        }
    }

    private fun setReleaseDate(binding: MovieSearchItemBinding, releaseDate: String?) {
        if (releaseDate != null) {
            binding.tvYear.text = releaseDate
            binding.tvYear.visibility = VISIBLE
            binding.tvYearLabel.visibility = VISIBLE
        } else {
            binding.tvYear.visibility = GONE
            binding.tvYearLabel.visibility = GONE
        }
    }

    private fun setDuration(binding: MovieSearchItemBinding, duration: String?) {
        if (duration != null) {
            binding.tvDuration.text = duration
            binding.tvDuration.visibility = VISIBLE
            binding.tvDurationLabel.visibility = VISIBLE
        } else {
            binding.tvDuration.visibility = GONE
            binding.tvDurationLabel.visibility = GONE
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