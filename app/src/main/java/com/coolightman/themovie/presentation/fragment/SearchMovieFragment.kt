package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentSearchMovieBinding
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.presentation.adapter.SearchMovieAdapter
import com.coolightman.themovie.presentation.viewmodel.SearchMovieViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class SearchMovieFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchMovieViewModel::class.java]
    }

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchMovieAdapter: SearchMovieAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        focusOnInputText()
        createRecycler()
        createObserver()
        listeners()
    }

    private fun focusOnInputText() {
        binding.tfSearchKeywords.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun listeners() {
        with(binding) {
            btSearch.setOnClickListener {
                val keywords = tfSearchKeywords.text
                Toast.makeText(requireContext(), "Search: $keywords", Toast.LENGTH_SHORT).show()
                viewModel.searchMovies(keywords.toString())
            }
        }
    }

    private fun createObserver() {
        viewModel.getMovieSearchList().observe(viewLifecycleOwner){
            it?.let {
                searchMovieAdapter.submitList(it)
            }
        }
    }

    private fun createRecycler() {
        val recycler = binding.rvSearchResult
        createSearchMovieAdapter(recycler)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun createSearchMovieAdapter(recycler: RecyclerView) {
        searchMovieAdapter = SearchMovieAdapter { onMovieClickListener(it) }
        recycler.adapter = searchMovieAdapter
        searchMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onMovieClickListener(movie: MovieSearch) {
        Toast.makeText(requireContext(), "${movie.movieId}", Toast.LENGTH_SHORT).show()
    }

}