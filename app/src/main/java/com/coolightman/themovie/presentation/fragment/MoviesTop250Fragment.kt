package com.coolightman.themovie.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding
import com.coolightman.themovie.databinding.FragmentMoviesTop250Binding

class MoviesTop250Fragment : Fragment() {

    private var _binding: FragmentMoviesTop250Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesTop250Binding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MoviesTop250Fragment()
    }
}