package com.coolightman.themovie.presentation.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolightman.themovie.R
import com.coolightman.themovie.presentation.viewmodel.AllReviewsViewModel

class AllReviewsFragment : Fragment() {

    companion object {
        fun newInstance() = AllReviewsFragment()
    }

    private lateinit var viewModel: AllReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_reviews, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllReviewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}