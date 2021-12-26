package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding
import com.coolightman.themovie.di.DaggerApplicationComponent
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.presentation.adapter.ShortMovieAdapter
import com.coolightman.themovie.presentation.viewmodel.MainViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MoviesPopularFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentMoviesPopularBinding? = null
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
        _binding = FragmentMoviesPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
        createAdapter()
    }

    private fun createObserver() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    shortMovieAdapter.submitList(it)
                }
            }
        }
    }

    private fun createAdapter() {
        shortMovieAdapter = ShortMovieAdapter { onMovieClick(it) }
        binding.rvPopularMovies.adapter = shortMovieAdapter
        shortMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
    }
}