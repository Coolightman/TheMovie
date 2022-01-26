package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentSearchMovieBinding
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
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var inputMethodManager: InputMethodManager

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showKeyboardOnInputText()
        createRecycler()
        observers()
        listeners()
    }

    private fun showKeyboardOnInputText() {
        binding.tfSearchKeywords.requestFocus()
        inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    private fun listeners() {
        errorsListener()
        with(binding) {
            btSearch.setOnClickListener {
                startSearch()
            }

            tfSearchKeywords.setOnEditorActionListener { view, actionId, keyEvent ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        startSearch()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun errorsListener() {
        viewModel.errorMessage.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.resetError()
            }
        }
    }

    private fun startSearch() {
        val keywords = binding.tfSearchKeywords.text
        hideClue()
        showProgressBar()
        viewModel.searchMovies(keywords.toString())
    }

    private fun showProgressBar() {
        binding.progressBarSearch.visibility = VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarSearch.visibility = INVISIBLE
    }

    private fun observers() {
        observeList()
        observerProgressHide()
    }

    private fun observerProgressHide() {
        viewModel.searchLoaded.observe(viewLifecycleOwner) {
            hideProgressBar()
            checkEmptyResult()
        }
    }

    private fun checkEmptyResult() {
        if (layoutManager.itemCount == 0) {
            showClue()
        }
    }

    private fun observeList() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            searchMovieAdapter.submitList(it)
            if (it.isNotEmpty()) {
                hideClue()
            }
        }
    }

    private fun showClue() {
        binding.tvClueEmptySearch.visibility = VISIBLE
    }

    private fun hideClue() {
        binding.tvClueEmptySearch.visibility = INVISIBLE
    }

    private fun createRecycler() {
        val recycler = binding.rvSearchResult
        createSearchMovieAdapter(recycler)
        layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun createSearchMovieAdapter(recycler: RecyclerView) {
        searchMovieAdapter = SearchMovieAdapter { onMovieClickListener(it.movieId) }
        recycler.adapter = searchMovieAdapter
        searchMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onMovieClickListener(movieId: Long) {
        hideKeyboard()
        launchMovieDetail(movieId)
    }

    private fun launchMovieDetail(movieId: Long) {
        findNavController().navigate(
            SearchMovieFragmentDirections.actionSearchMovieFragmentToMovieDetailFragment(movieId)
        )
    }

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}