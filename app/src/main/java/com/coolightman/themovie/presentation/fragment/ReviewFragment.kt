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
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentReviewBinding
import com.coolightman.themovie.domain.entity.ReviewType
import com.coolightman.themovie.presentation.viewmodel.ReviewViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.coolightman.themovie.util.CardColor
import javax.inject.Inject

class ReviewFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<ReviewFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ReviewViewModel::class.java]
    }

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        val reviewNumber = args.number
        viewModel.setMovieId(movieId)

        createObserver(reviewNumber)
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

    private fun createObserver(reviewNumber: Int) {
        viewModel.reviews.observe(viewLifecycleOwner) {
            val review = it[reviewNumber]
            setReviewTitle(review.title)
            setReviewDescription(review.description)
            setReviewColor(review.type)
            setReviewAuthor(review.author)
        }
    }

    private fun setReviewAuthor(author: String) {
        val view = binding.includedReviewItem.tvReviewAuthor
        view.text = author
    }

    private fun setReviewColor(type: ReviewType) {
        val card = binding.includedReviewItem.cvSingleReview
        CardColor.setCardColor(card, type, requireContext())
    }

    private fun setReviewDescription(revDescription: String) {
        binding.includedReviewItem.tvReviewDescription.text = revDescription
    }

    private fun setReviewTitle(title: String) {
        val view = binding.includedReviewItem.tvReviewTitle
        if (title.isNotEmpty()) {
            val text = "\"$title\""
            view.text = text
        } else {
            view.visibility = View.GONE
        }
    }
}