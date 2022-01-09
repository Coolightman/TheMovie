package com.coolightman.themovie.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.util.ColumnCount.getColumnCount

class MoviesPopularFragment : Fragment() {

    private val viewModel by lazy {
        (requireParentFragment() as MainFragment).viewModel
    }

    private var _binding: FragmentMoviesPopularBinding? = null
    private val binding get() = _binding!!

    private val sharedPref by lazy {
        requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
    }

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
        checkLastRefresh()
    }

    private fun checkLastRefresh() {
        val lastRefresh = sharedPref.getLong(PREF_POPULAR_LAST_REFRESH, 0)
        if (lastRefresh != 0L) {
            val currentTime = System.currentTimeMillis()
            val diff = currentTime - lastRefresh
            val hours = (diff / 1000) / 3600
            if (hours >= TIME_AUTO_REFRESH_HOURS) {
                refreshPopular()
            }
        } else {
            refreshPopular()
        }
    }

    private fun swipeRefreshListener() {
        binding.swipeRefreshPopular.setOnRefreshListener {
            refreshPopular()
            binding.swipeRefreshPopular.isRefreshing = false
        }
    }

    private fun refreshPopular() {
        viewModel.refreshPopularMovies()
        createRefreshTimeStamp()
    }

    @SuppressLint("CommitPrefEdits")
    private fun createRefreshTimeStamp() {
        val stamp = System.currentTimeMillis()
        sharedPref.edit().putLong(PREF_POPULAR_LAST_REFRESH, stamp).apply()
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
        shortMovieAdapter = ShortMovieAdapter { onClickMovie(it.movieId) }
        shortMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
        fun newInstance() = MoviesPopularFragment()

        private const val MIN_PAGE_SIZE = 20
        private const val PREF_POPULAR_LAST_REFRESH = "PopularRefreshStamp"
        private const val TIME_AUTO_REFRESH_HOURS = 8
    }
}