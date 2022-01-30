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
import com.coolightman.themovie.databinding.FragmentAllReviewsBinding
import com.coolightman.themovie.presentation.adapter.ReviewAdapter
import com.coolightman.themovie.presentation.viewmodel.AllReviewsViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class AllReviewsFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<AllReviewsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AllReviewsViewModel::class.java]
    }

    private var _binding: FragmentAllReviewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        viewModel.setMovieId(movieId)

        createRecycler(movieId)
        createObserver()
        listeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createRecycler(movieId: Long) {
        val recycler = binding.rvAllReviews
        createReviewsAdapter(recycler, movieId)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun createReviewsAdapter(recycler: RecyclerView, movieId: Long) {
        reviewAdapter = ReviewAdapter { onReviewClickListener(it, movieId) }
        recycler.adapter = reviewAdapter
        reviewAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onReviewClickListener(number: Int, movieId: Long) {
        findNavController().navigate(
            AllReviewsFragmentDirections.actionAllReviewsFragmentToReviewFragment(
                movieId,
                number
            )
        )
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

    private fun createObserver() {
        viewModel.reviews.observe(viewLifecycleOwner) {
            reviewAdapter.submitList(it)
        }
    }

}