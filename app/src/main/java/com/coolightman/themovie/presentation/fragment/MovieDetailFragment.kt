package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMovieDetailBinding
import com.coolightman.themovie.domain.entity.Country
import com.coolightman.themovie.domain.entity.Genre
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.presentation.viewmodel.MovieDetailViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.coolightman.themovie.util.RatingColor.setRatingColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var movie: Movie

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = getMovieIdArg()
        lifecycleScope.launch {
            val job = launch { viewModel.loadMovieDetails(movieId) }
            job.join()
            createObservers(movieId)
            createListeners(movieId)
        }
    }

    private fun createListeners(movieId: Long) {
        binding.imgFavorite.setOnClickListener {
            if (movie.isFavorite) {
                viewModel.removeMovieFromFavorite(movieId)
                shortToast(getString(R.string.favorite_deleted))
            } else {
                viewModel.addMovieToFavorite(movieId)
                shortToast(getString(R.string.favorite_added))
            }
        }
    }

    private fun shortToast(text: String) {
        lifecycleScope.launch {
            val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
            toast.show()
            delay(700)
            toast.cancel()
        }
    }

    private fun getMovieIdArg() =
        requireArguments().getLong(ARG_MOVIE_ID, 0)

    private fun createObservers(movieId: Long) {
        viewModel.getMovie(movieId).observe(viewLifecycleOwner) {
            it?.let {
                Log.d("ObservingMovie", it.toString())
                movie = it
                setPoster(it.poster)
                setRating(it)
                setRatingCount(it)
                setFavorite(it.isFavorite)
                setNameOrigin(it.nameOriginal)
                setNameRu(it.nameRu)
                setSlogan(it.slogan)
                setYear(it.releaseDate)
                setCountries(it.countries)
                setDuration(it.duration)
                setGenres(it.genres)
                setDescription(it.description)
            }
        }
    }

    private fun setNameOrigin(nameOriginal: String?) {
        if (nameOriginal != null) {
            binding.tvNameOriginal.text = nameOriginal
        } else {
            binding.tvNameOriginal.visibility = GONE
        }
    }

    private fun setNameRu(nameRu: String?) {
        if (nameRu != null) {
            binding.tvNameRu.text = nameRu
        } else {
            binding.tvNameRu.visibility = GONE
        }
    }

    private fun setSlogan(slogan: String?) {
        if (slogan != null) {
            binding.tvSlogan.text = slogan
        } else {
            binding.tvSlogan.visibility = GONE
        }
    }

    private fun setYear(releaseDate: String?) {
        if (releaseDate != null) {
            binding.tvYear.text = releaseDate
        } else {
            binding.tvYear.visibility = GONE
            binding.tvYearLabel.visibility = GONE
        }
    }

    private fun setCountries(countries: List<Country>) {
        if (countries.isNotEmpty()) {
            val names = countries.map { it.name }
            val text = getStringWithEnters(names)
            binding.tvCountry.text = text
        } else {
            binding.tvCountry.visibility = GONE
            binding.tvCountryLabel.visibility = GONE
        }
    }

    private fun getStringWithEnters(list: List<String>): StringBuilder {
        val entersString = StringBuilder()
        for ((i, element) in list.withIndex()) {
            if (i == list.size - 1) entersString.append(element)
            else entersString.append("$element\n")
        }
        return entersString
    }

    private fun setDuration(duration: String?) {
        if (duration != null) {
            binding.tvDuration.text = duration
        } else {
            binding.tvDuration.visibility = GONE
            binding.tvDurationLabel.visibility = GONE
        }
    }

    private fun setGenres(genres: List<Genre>) {
        if (genres.isNotEmpty()) {
            val names = genres.map { it.name }
            val text = getStringWithEnters(names)
            binding.tvGenre.text = text
        } else {
            binding.tvGenre.visibility = GONE
            binding.tvGenreLabel.visibility = GONE
        }
    }

    private fun setDescription(description: String?) {
        if (description != null) {
            binding.tvDescription.text = description
        } else {
            binding.tvDescription.visibility = GONE
        }
    }

    private fun setFavorite(favorite: Boolean) {
        binding.imgFavorite.visibility = VISIBLE
        if (favorite) setStarToOrange()
        else setStarToGrey()
    }

    private fun setStarToGrey() {
        binding.imgFavorite.setColorFilter(
            ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
        )
    }

    private fun setStarToOrange() {
        binding.imgFavorite.setColorFilter(
            ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light)
        )
    }

    private fun setRating(movie: Movie) {
        val rating = movie.rating
        val ratingAwait = movie.ratingAwait
        when {
            rating != null -> {
                binding.tvRating.text = rating
                setRatingColor(binding.tvRating, rating)
            }
            ratingAwait != null -> {
                binding.tvRating.text = ratingAwait
                setRatingColor(binding.tvRating, ratingAwait)
            }
            else -> {
                binding.tvRating.visibility = INVISIBLE
                binding.tvRatingCount.visibility = INVISIBLE
            }
        }
    }

    private fun setRatingCount(movie: Movie) {
        val ratingCount = movie.ratingCount
        val ratingAwaitCount = movie.ratingAwaitCount
        when {
            ratingCount != null && ratingCount != "0" -> {
                binding.tvRatingCount.text = ratingCount
            }
            ratingAwaitCount != null && ratingAwaitCount != "0" -> {
                binding.tvRatingCount.text = ratingAwaitCount
            }
            else -> {
                binding.tvRatingCount.visibility = GONE
            }
        }
    }


    private fun setPoster(poster: String?) {
        poster?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.placeholder_image_poster)
                .into(binding.imgPoster)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_MOVIE_ID = "movieId"

        fun newInstance(movieId: Long) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_MOVIE_ID, movieId)
                }
            }
    }
}