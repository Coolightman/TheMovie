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
import com.coolightman.themovie.domain.entity.*
import com.coolightman.themovie.presentation.adapter.FrameAdapter
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.presentation.adapter.StaffPreviewAdapter
import com.coolightman.themovie.presentation.adapter.VideoAdapter
import com.coolightman.themovie.presentation.viewmodel.MovieDetailViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.coolightman.themovie.util.CardColor.setCardColor
import com.coolightman.themovie.util.RatingColor.setRatingColor
import com.coolightman.themovie.util.TextFormat.cutTextSize
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
    private lateinit var staffPreviewAdapter: StaffPreviewAdapter
    private var reviewNumber = -1

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
        createStaffPreviewRecycler()
    }

    private fun createStaffPreviewRecycler() {
        val recycler = binding.rvStaff
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        createStaffAdapter(recycler)
    }

    private fun createStaffAdapter(recycler: RecyclerView) {
        staffPreviewAdapter = StaffPreviewAdapter { onStaffClickListener(it) }
        recycler.adapter = staffPreviewAdapter
        staffPreviewAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onStaffClickListener(staff: Staff) {
        shortToast("Launch Staff ${staff.staffId}")
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
        similarAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
        videoAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
        frameAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
                launchALlReviewsFragment()
            }

            tvStaffSeeMore.setOnClickListener {
                launchStaffFragment()
            }

            cvReview1.setOnClickListener {
                launchReviewFragment()
            }
        }
    }

    private fun launchStaffFragment() {
        findNavController().navigate(
            MovieDetailFragmentDirections.actionMovieDetailFragmentToStaffFragment(movie.movieId)
        )
    }

    private fun launchALlReviewsFragment() {
        findNavController().navigate(
            MovieDetailFragmentDirections.actionMovieDetailFragmentToAllReviewsFragment(movie.movieId)
        )
    }

    private fun launchReviewFragment() {
        findNavController().navigate(
            MovieDetailFragmentDirections.actionMovieDetailFragmentToReviewFragment(
                movie.movieId,
                reviewNumber
            )
        )
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
        createReviewsObserver(movieId)
        createVideosObserver(movieId)
        createSimilarsObserver(movieId)
        createStaffObserver(movieId)
    }

    private fun createStaffObserver(movieId: Long) {
        viewModel.getStaff(movieId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.d("ObservingStaff", it.toString())
                    checkStuffSize(it)
                    val list = it.take(NUMBER_PREVIEW_STAFF)
                    staffPreviewAdapter.submitList(list)
                } else {
                    binding.cvStaff.visibility = GONE
                }
            }
        }
    }

    private fun checkStuffSize(it: List<Staff>) {
        if (it.size == 1) {
            binding.tvStaffSeeMore.visibility = GONE
        }
    }

    private fun createReviewsObserver(movieId: Long) {
        viewModel.getReviews(movieId).observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    Log.d("ObservingReviews", it.toString())
                    val reviewRndNumb = getRandomNumber(it.size)
                    val review = it[reviewRndNumb]
                    reviewNumber = reviewRndNumb
                    checkReviewsSize(it)
                    setReviewTitle(review.title)
                    setReviewDescription(review.description)
                    setReviewColor(review.type)
                } else {
                    binding.cvReviews.visibility = GONE
                }
            } else {
                binding.cvReviews.visibility = GONE
            }
        }
    }

    private fun checkReviewsSize(it: List<Review>) {
        if (it.size == 1) {
            binding.tvReviewsSeeMore.visibility = GONE
        }
    }

    private fun setReviewColor(type: ReviewType) {
        val card = binding.cvReview1
        setCardColor(card, type, requireContext())
    }

    private fun setReviewDescription(revDescription: String) {
        val description = cutTextSize(
            revDescription,
            MAX_REVIEW_DESCRIPTION_SIZE
        ) + getString(R.string.tv_read_more)
        binding.tvReview1Description.text = description
    }

    private fun setReviewTitle(title: String) {
        if (title.isNotEmpty()) {
            val text = "\"$title\""
            binding.tvReview1Title.text = text
        } else {
            binding.tvReview1Title.visibility = GONE
        }
    }

    private fun createFactsObserver(movieId: Long) {
        viewModel.getFacts(movieId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.d("ObservingFacts", it.toString())
                    val factRndNumb = getRandomNumber(it.size)
                    val fact = it[factRndNumb]
                    checkFactsSize(it)
                    checkSpoiler(fact)
                    binding.tvFact1.text = fact.text
                } else {
                    binding.cvFacts.visibility = GONE
                }
            }
        }
    }

    private fun getRandomNumber(listSize: Int): Int {
        return if (listSize > 1) {
            val max = listSize - 1
            val min = 0
            (Math.random() * (max - min + 1) + min).toInt()
        } else {
            FIRST_LIST_ITEM
        }
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
            if (it != NON_TOP_250_place) {
                binding.tvTop250Place.text = it
                binding.viewTop250.visibility = VISIBLE
            }
        }
    }

    private fun setCriticRating(movie: Movie) {
        val criticRating = movie.ratingCritics
        if (criticRating != null) {
            binding.tvRatingCritics.text = criticRating
            binding.tvRatingCriticsCount.text = movie.ratingCriticsCount
            setRatingColor(binding.tvRatingCritics, criticRating)
            binding.viewCritics.visibility = VISIBLE
        }
    }

    private fun setImdbRating(movie: Movie) {
        val imdbRating = movie.ratingImdb
        if (imdbRating != null) {
            binding.tvRatingImdb.text = imdbRating
            binding.tvRatingImdbCount.text = movie.ratingImdbCount
            setRatingColor(binding.tvRatingImdb, imdbRating)
            binding.viewImdb.visibility = VISIBLE
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
        private const val NON_TOP_250_place = "0"
        private const val FIRST_LIST_ITEM = 0
        private const val MAX_REVIEW_DESCRIPTION_SIZE = 200
        private const val NUMBER_PREVIEW_STAFF = 6
    }
}