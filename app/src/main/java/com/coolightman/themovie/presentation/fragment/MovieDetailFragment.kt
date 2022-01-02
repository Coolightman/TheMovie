package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMovieDetailBinding
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.presentation.viewmodel.MovieDetailViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.coolightman.themovie.util.GlideApp
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
        createObservers(movieId)
    }

    private fun getMovieIdArg() =
        requireArguments().getLong(ARG_MOVIE_ID, 0)

    private fun createObservers(movieId: Long) {
        viewModel.getMovieInfo(movieId).observe(viewLifecycleOwner) {
            Log.d("ObservingMovie", it.toString())
            it?.let {
                setPoster(it.poster)
                setPlaces(it)
                setRating(it.rating)
                setRatingCount(it.ratingCount)
                setFavorite(it.isFavorite)
            }
        }
    }

    private fun setFavorite(favorite: Boolean) {
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

    private fun setRatingCount(ratingCount: Int?) {
        ratingCount?.let {
            val count = it.toString()
            binding.tvRatingCount.text = count
        }
    }

    private fun setRating(rating: String?) {
        rating?.let {
            binding.tvRating.text = it
        }
    }

    private fun setPlaces(movie: Movie) {
        setPopularPlace(movie.topPopularPlace)
        setTop250Place(movie.top250Place)
    }

    private fun setTop250Place(top250Place: String) {
        if (top250Place != "0") {
            binding.tvTop250Place.text = top250Place
        } else {
            binding.tvTop250Place.visibility = GONE
            binding.tvTop250PlaceLabel.visibility = GONE
        }
    }

    private fun setPopularPlace(topPopularPlace: String) {
        if (topPopularPlace != "0") {
            binding.tvPopularPlace.text = topPopularPlace
        } else {
            binding.tvPopularPlace.visibility = GONE
            binding.tvPopularPlaceLabel.visibility = GONE
        }
    }

    private fun setPoster(poster: String?) {
        poster?.let {
            GlideApp.with(this)
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