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
import com.coolightman.themovie.databinding.FragmentMoviesTop250Binding
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.util.ColumnCount.getColumnCount

class MoviesTop250Fragment : Fragment() {

    private val viewModel by lazy {
        (requireParentFragment() as MainFragment).viewModel
    }

    private var _binding: FragmentMoviesTop250Binding? = null
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
        _binding = FragmentMoviesTop250Binding.inflate(inflater, container, false)
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
        val lastRefresh = sharedPref.getLong(PREF_TOP_250_LAST_REFRESH, 0)
        if (lastRefresh != 0L) {
            val currentTime = System.currentTimeMillis()
            val diff = currentTime - lastRefresh
            val hours = (diff / 1000) / 3600
            if (hours >= TIME_AUTO_REFRESH_HOURS) {
                refreshTop250()
            }
        } else {
            refreshTop250()
        }
    }

    private fun swipeRefreshListener() {
        binding.swipeRefreshTop250.setOnRefreshListener {
            refreshTop250()
            binding.swipeRefreshTop250.isRefreshing = false
        }
    }

    private fun refreshTop250() {
        viewModel.refreshTop250Movies()
        createRefreshTimeStamp()
    }

    @SuppressLint("CommitPrefEdits")
    private fun createRefreshTimeStamp() {
        val stamp = System.currentTimeMillis()
        sharedPref.edit().putLong(PREF_TOP_250_LAST_REFRESH, stamp).apply()
    }

    private fun createObserver() {
        viewModel.getTop250Movies().observe(viewLifecycleOwner) {
            if (it.size >= MIN_PAGE_SIZE) {
                shortMovieAdapter.submitList(it)
            }
        }
    }

    private fun createRecyclerView() {
        val recycler = binding.rvTop250Movies
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
                    viewModel.loadTop250NextPage()
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
        fun newInstance() = MoviesTop250Fragment()
        private const val MIN_PAGE_SIZE = 20
        private const val PREF_TOP_250_LAST_REFRESH = "Top250RefreshStamp"
        private const val TIME_AUTO_REFRESH_HOURS = 120
    }
}