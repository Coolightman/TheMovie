package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMovieDetailBinding
import com.coolightman.themovie.domain.entity.Country
import com.coolightman.themovie.domain.entity.Fact
import com.coolightman.themovie.domain.entity.Genre
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.presentation.adapter.FrameAdapter
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.presentation.adapter.VideoAdapter
import com.coolightman.themovie.presentation.viewmodel.MovieDetailViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.coolightman.themovie.util.RatingColor.setRatingColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<MovieDetailFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var frameAdapter: FrameAdapter
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var similarAdapter: ShortMovieAdapter

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
        val movieId = args.movieId
        createObservers(movieId)
        createRecyclers()
        createListeners(movieId)
    }

    private fun createRecyclers() {
        createFrameRecycler()
        createVideoRecycler()
        createSimilarRecycler()
    }

    private fun createSimilarRecycler() {
        val recycler = binding.rvSimilars
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        createSimilarAdapter(recycler)
    }

    private fun createSimilarAdapter(recycler: RecyclerView) {
        similarAdapter = ShortMovieAdapter { onSimilarClickListener(it.movieId) }
        recycler.adapter = similarAdapter
    }

    private fun onSimilarClickListener(movieId: Long) {
        findNavController().navigate(
            MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(movieId)
        )
    }

    private fun createVideoRecycler() {
        val recycler = binding.rvVideos
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        createVideoAdapter(recycler)
    }

    private fun createVideoAdapter(recycler: RecyclerView) {
        videoAdapter = VideoAdapter { onVideoItemClickListener(it) }
        recycler.adapter = videoAdapter
    }

    private fun onVideoItemClickListener(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun createFrameRecycler() {
        val recycler = binding.rvFrames
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        createFrameAdapter(recycler)
    }

    private fun createFrameAdapter(recycler: RecyclerView) {
        frameAdapter = FrameAdapter { onFrameItemClickListener(it) }
        recycler.adapter = frameAdapter
    }

    private fun onFrameItemClickListener(framePosition: Int) {
        findNavController().navigate(
            MovieDetailFragmentDirections.actionMovieDetailFragmentToGalleryFragment(
                movie.movieId,
                framePosition
            )
        )
    }

    private fun createListeners(movieId: Long) {
        with(binding) {
            imgFavorite.setOnClickListener {
                if (movie.isFavorite) {
                    viewModel.removeMovieFromFavorite(movieId)
                    shortToast(getString(R.string.favorite_deleted))
                } else {
                    viewModel.addMovieToFavorite(movieId)
                    shortToast(getString(R.string.favorite_added))
                }
            }

            tvButtonKinopoisk.setOnClickListener {
                launchToKinopoisk()
            }

            tvFactsSeeMore.setOnClickListener {
                launchToAllFacts()
            }

            tvReviewsSeeMore.setOnClickListener {
                shortToast("Go to AllReviewsFragment")
            }
        }
    }

    private fun launchToAllFacts() {
        findNavController().navigate(
            MovieDetailFragmentDirections.actionMovieDetailFragmentToAllFactsFragment(
                movie.movieId
            )
        )
    }

    private fun launchToKinopoisk() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.webUrl))
        startActivity(intent)
    }

    private fun shortToast(text: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
            toast.show()
            delay(TIME_SHORT_TOAST)
            toast.cancel()
        }
    }

    private fun createObservers(movieId: Long) {
        createMovieObserver(movieId)
        createFramesObserver(movieId)
        createFactsObserver(movieId)
        createVideosObserver(movieId)
        createSimilarsObserver(movieId)
    }

    private fun createFactsObserver(movieId: Long) {
        viewModel.getFacts(movieId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    val previewFact = getRandomFact(it)
                    checkFactsSize(it)
                    checkSpoiler(previewFact)
                    binding.tvFact1.text = previewFact.text
                } else {
                    binding.cvFacts.visibility = GONE
                }
            }
        }
    }

    private fun getRandomFact(list: List<Fact>): Fact {
        return if (list.size > 1) {
            val rnd = getRandomNumber(list.size)
            list[rnd]
        } else {
            list[0]
        }
    }

    private fun getRandomNumber(listSize: Int): Int {
        val max = listSize - 1
        val min = 0
        return (Math.random() * (max - min + 1) + min).toInt()
    }


    private fun checkFactsSize(it: List<Fact>) {
        if (it.size == 1) {
            binding.tvFactsSeeMore.visibility = GONE
        }
    }

    private fun checkSpoiler(fact: Fact) {
        if (!fact.spoiler) {
            binding.tvFact1Warning.visibility = GONE
        }
    }

    private fun createSimilarsObserver(movieId: Long) {
        viewModel.getSimilars(movieId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.d("ObservingSimilars", it.toString())
                    similarAdapter.submitList(it)
                } else {
                    binding.cvSimilars.visibility = GONE
                }
            }
        }
    }

    private fun createVideosObserver(movieId: Long) {
        viewModel.getVideos(movieId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.d("ObservingVideos", it.toString())
                    videoAdapter.submitList(it)
                } else {
                    binding.cvVideos.visibility = GONE
                }
            }
        }
    }

    private fun createFramesObserver(movieId: Long) {
        viewModel.getFrames(movieId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.d("ObservingFrames", it.toString())
                    frameAdapter.submitList(it)
                } else {
                    binding.cvFrames.visibility = GONE
                }

            }
        }
    }

    private fun createMovieObserver(movieId: Long) {
        viewModel.getMovie(movieId).observe(viewLifecycleOwner) {
            it?.let {
                Log.d("ObservingMovie", it.toString())
                movie = it
                setPoster(it.poster)
                setRating(it)
                setRatingCount(it)
                setImdbRating(it)
                setCriticRating(it)
                setTop250Place(it)
                setFavorite(it.isFavorite)
                setNameOrigin(it.nameOriginal)
                setNameRu(it.nameRu)
                setSlogan(it.slogan)
                setYear(it.releaseDate)
                setCountries(it.countries)
                setDuration(it.duration)
                setGenres(it.genres)
                setAgeLimit(it.ageLimit)
                setDescription(it.description)
            }
        }
    }

    private fun setTop250Place(movie: Movie) {
        viewModel.getTop250Place(movie.movieId).observe(viewLifecycleOwner) {
            if (it != "0") {
                binding.tvTop250Place.text = it
            } else {
                binding.viewTop250.visibility = GONE
            }
        }
    }

    private fun setCriticRating(movie: Movie) {
        val criticRating = movie.ratingCritics
        if (criticRating != null) {
            binding.tvRatingCritics.text = criticRating
            binding.tvRatingCriticsCount.text = movie.ratingCriticsCount
            setRatingColor(binding.tvRatingCritics, criticRating)
        } else {
            binding.viewCritics.visibility = GONE
        }
    }

    private fun setImdbRating(movie: Movie) {
        val imdbRating = movie.ratingImdb
        if (imdbRating != null) {
            binding.tvRatingImdb.text = imdbRating
            binding.tvRatingImdbCount.text = movie.ratingImdbCount
            setRatingColor(binding.tvRatingImdb, imdbRating)
        } else {
            binding.viewImdb.visibility = GONE
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
            val text = "\"$slogan\""
            binding.tvSlogan.text = text
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

    private fun setAgeLimit(ageLimit: String?) {
        if (ageLimit != null) {
            val text = "${ageLimit.substring(3)}+"
            binding.tvAgeLimit.text = text
        } else {
            binding.tvAgeLimit.visibility = GONE
            binding.tvAgeLimitLabel.visibility = GONE
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
        private const val TIME_SHORT_TOAST = 800L
    }
}