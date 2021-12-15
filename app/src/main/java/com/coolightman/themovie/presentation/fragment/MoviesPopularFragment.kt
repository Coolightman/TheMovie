package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding

class MoviesPopularFragment : Fragment() {

    private var _binding: FragmentMoviesPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MoviesPopularFragment()
    }
}