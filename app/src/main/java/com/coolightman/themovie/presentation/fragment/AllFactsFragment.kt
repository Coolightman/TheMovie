package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentAllFactsBinding
import com.coolightman.themovie.presentation.adapter.FactsAdapter
import com.coolightman.themovie.presentation.viewmodel.AllFactsViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class AllFactsFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<AllFactsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AllFactsViewModel::class.java]
    }

    private var _binding: FragmentAllFactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var factsAdapter: FactsAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        viewModel.setMovieId(movieId)

        createRecycler()
        createObserver()
        createListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createListeners() {
        with(binding) {
            imgCloseFragment.setOnClickListener {
                closeFragment()
            }
        }
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }

    private fun createObserver() {
        viewModel.facts.observe(viewLifecycleOwner) {
            factsAdapter.submitList(it)
        }
    }

    private fun createRecycler() {
        val recycler = binding.rvAllFacts
        createFactsAdapter(recycler)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun createFactsAdapter(recycler: RecyclerView) {
        factsAdapter = FactsAdapter()
        recycler.adapter = factsAdapter
        factsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

}