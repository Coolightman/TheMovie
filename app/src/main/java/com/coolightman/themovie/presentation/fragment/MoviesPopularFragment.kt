package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter

class MoviesPopularFragment : Fragment() {

    private val viewModel by lazy {
        (requireParentFragment() as MainFragment).viewModel
    }

    private var _binding: FragmentMoviesPopularBinding? = null
    private val binding get() = _binding!!

    private lateinit var shortMovieAdapter: ShortMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
        createRecyclerView()
    }

    private fun createObserver() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    shortMovieAdapter.submitList(it)
                } else {
                    viewModel.loadPopularNextPage()
                }
            }
        }
    }

    private fun createRecyclerView() {
        val recycler = binding.rvPopularMovies
        createAdapter()
        recycler.adapter = shortMovieAdapter
        recycler.layoutManager = GridLayoutManager(requireContext(), getColumnCount())
        createInfinityScrollListener(recycler)
    }

    private fun createInfinityScrollListener(recycler: RecyclerView) {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy != 0){
                    viewModel.loadPopularNextPage()
                }
            }
        })
    }

    private fun createAdapter() {
        shortMovieAdapter = ShortMovieAdapter { onMovieClick(it) }
        shortMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun getColumnCount(): Int {
        val displayWidth = resources.displayMetrics.widthPixels
        val columns = displayWidth / IMAGE_WIDTH
        return if (columns > MIN_COLUMN) columns
        else MIN_COLUMN
    }

    private fun onMovieClick(movie: ShortMovie) {
        Toast.makeText(requireContext(), "${movie.movieId}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MoviesPopularFragment()

        private const val IMAGE_WIDTH = 360
        private const val MIN_COLUMN = 2
    }
}