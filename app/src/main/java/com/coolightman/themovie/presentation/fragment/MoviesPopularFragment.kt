package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.util.ColumnCount.getColumnCount

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
        swipeRefreshListener()
    }

    private fun swipeRefreshListener() {
        binding.swipeRefreshPopular.setOnRefreshListener {
            viewModel.refreshPopularMovies()
            binding.swipeRefreshPopular.isRefreshing = false
        }
    }

    private fun createObserver() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            if (it.size >= MIN_PAGE_SIZE) {
                shortMovieAdapter.submitList(it)
            }
        }
    }

    private fun createRecyclerView() {
        val recycler = binding.rvPopularMovies
        createAdapter()
        recycler.adapter = shortMovieAdapter
        recycler.layoutManager = GridLayoutManager(requireContext(), getColumnCount(resources))
        createInfinityScrollListener(recycler)
    }

    private fun createInfinityScrollListener(recycler: RecyclerView) {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    viewModel.loadPopularNextPage()
                }
            }
        })
    }

    private fun createAdapter() {
        shortMovieAdapter = ShortMovieAdapter { onClickMovie(it) }
        shortMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onClickMovie(movie: ShortMovie) {
        val fragment = MovieDetailFragment.newInstance(movie.movieId)
        requireParentFragment().parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MoviesPopularFragment()
        private const val MIN_PAGE_SIZE = 20
    }
}