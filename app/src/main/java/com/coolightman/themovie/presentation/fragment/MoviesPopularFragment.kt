package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coolightman.themovie.databinding.FragmentMoviesPopularBinding
import com.coolightman.themovie.di.DaggerApplicationComponent
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

    override fun onAttach(context: Context) {
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
                    Log.d("MYLOGObs", it.toString())
                }
            }
        }
    }

    private fun createAdapter() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MoviesPopularFragment()
    }
}