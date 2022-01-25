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
import com.coolightman.themovie.databinding.FragmentPersonFactsBinding
import com.coolightman.themovie.presentation.adapter.PersonFactsAdapter
import com.coolightman.themovie.presentation.viewmodel.PersonViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class PersonFactsFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<PersonFactsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PersonViewModel::class.java]
    }

    private var _binding: FragmentPersonFactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var factsAdapter: PersonFactsAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonFactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personId = args.personId

        createObserver(personId)
        createRecycler()
        listeners()
    }

    private fun listeners() {
        with(binding) {
            imgCloseFragment.setOnClickListener {
                closeFragment()
            }
        }
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }

    private fun createObserver(movieId: Long) {
        viewModel.getPerson(movieId).observe(viewLifecycleOwner) {
            val list = it.facts
            factsAdapter.submitList(list)
        }
    }

    private fun createRecycler() {
        val recycler = binding.rvPersonFacts
        createFactsAdapter(recycler)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun createFactsAdapter(recycler: RecyclerView) {
        factsAdapter = PersonFactsAdapter()
        recycler.adapter = factsAdapter
        factsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

}