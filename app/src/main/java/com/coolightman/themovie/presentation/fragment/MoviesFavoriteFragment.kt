package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolightman.themovie.databinding.FragmentMoviesFavoriteBinding
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding

class MoviesFavoriteFragment : Fragment() {

    private var _binding: FragmentMoviesFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MoviesFavoriteFragment()
    }
}