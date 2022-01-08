package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMoviesFavoriteBinding
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.FavoriteMovieAdapter

class MoviesFavoriteFragment : Fragment() {

    private val viewModel by lazy {
        (requireParentFragment() as MainFragment).viewModel
    }

    private var _binding: FragmentMoviesFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
        createAdapter()
    }

    private fun createObserver() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                showClueAboutEmptyAdapter(false)
                favoriteMovieAdapter.submitList(it)
            } else {
                showClueAboutEmptyAdapter(true)
            }
        }
    }

    private fun showClueAboutEmptyAdapter(emptyAdapter: Boolean) {
        val visibility = if (emptyAdapter) VISIBLE
        else GONE
        binding.tvClueEmptyAdapter.visibility = visibility
    }

    private fun createAdapter() {
        favoriteMovieAdapter = FavoriteMovieAdapter { onClickMovie(it.movieId) }
        binding.rvFavoriteMovies.adapter = favoriteMovieAdapter
        binding.rvFavoriteMovies.layoutManager =
            GridLayoutManager(requireContext(), getColumnCount())
        favoriteMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun getColumnCount(): Int {
        val displayWidth = resources.displayMetrics.widthPixels
        val columns = displayWidth / IMAGE_WIDTH
        return if (columns > MIN_COLUMN) columns
        else MIN_COLUMN
    }

    private fun onClickMovie(movieId: Long) {
        requireParentFragment().findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMovieDetailFragment2(movieId)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MoviesFavoriteFragment()

        private const val IMAGE_WIDTH = 360
        private const val MIN_COLUMN = 2
    }
}