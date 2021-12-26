package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.databinding.FragmentMoviesTop250Binding
import com.coolightman.themovie.di.DaggerApplicationComponent
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.presentation.viewmodel.MainViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MoviesTop250Fragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentMoviesTop250Binding? = null
    private val binding get() = _binding!!

    private lateinit var shortMovieAdapter: ShortMovieAdapter

    override fun onAttach(context: Context) {
//        piece of shit
        DaggerApplicationComponent.factory().create(context).inject(this)
        super.onAttach(context)
    }

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
        createAdapter()
    }

    private fun createObserver() {
        viewModel.getTop250Movies().observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    shortMovieAdapter.submitList(it)
                }
            }
        }
    }

    private fun createAdapter() {
        shortMovieAdapter = ShortMovieAdapter { onMovieClick(it) }
        binding.rvTop250Movies.adapter = shortMovieAdapter
        binding.rvTop250Movies.layoutManager =
            GridLayoutManager(requireContext(), getColumnCount())
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
        fun newInstance() = MoviesTop250Fragment()
        private const val IMAGE_WIDTH = 360
        private const val MIN_COLUMN = 2
    }
}