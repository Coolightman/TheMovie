package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.databinding.FragmentMoviesFavoriteBinding
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.util.ColumnCount.getColumnCount

class MoviesFavoriteFragment : Fragment() {

    private val viewModel by lazy {
        (requireParentFragment() as MainFragment).viewModel
    }

    private var _binding: FragmentMoviesFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var shortMovieAdapter: ShortMovieAdapter

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
        createRecyclerView()
    }

    private fun createObserver() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            shortMovieAdapter.submitList(it)
            if (it.isNotEmpty()) {
                showClueAboutEmptyAdapter(false)
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

    private fun createRecyclerView() {
        val recycler = binding.rvFavoriteMovies
        createAdapter()
        recycler.adapter = shortMovieAdapter
        recycler.layoutManager = GridLayoutManager(requireContext(), getColumnCount(resources))
    }

    private fun createAdapter() {
        shortMovieAdapter = ShortMovieAdapter { onClickMovie(it.movieId) }
        shortMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onClickMovie(movieId: Long) {
        requireParentFragment().findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMovieDetailFragment2(movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MoviesFavoriteFragment()
    }
}