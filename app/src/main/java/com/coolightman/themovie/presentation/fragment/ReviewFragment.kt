package com.coolightman.themovie.presentation.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolightman.themovie.R
import com.coolightman.themovie.presentation.viewmodel.ReviewViewModel

class ReviewFragment : Fragment() {

    private lateinit var viewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}