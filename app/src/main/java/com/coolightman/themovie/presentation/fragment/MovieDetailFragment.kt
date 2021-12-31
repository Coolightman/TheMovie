package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentMovieDetailBinding
import com.coolightman.themovie.presentation.viewmodel.MovieDetailViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

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
        val movieId = getMovieIdArg()
        createDetailView(movieId)
    }

    private fun getMovieIdArg() =
        requireArguments().getLong(ARG_MOVIE_ID, 0)

    private fun createDetailView(movieId: Long) {
        binding.tvMovieId.text = movieId.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_MOVIE_ID = "movieId"

        fun newInstance(movieId: Long) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_MOVIE_ID, movieId)
                }
            }
    }
}