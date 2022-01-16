package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.Review

class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.reviewId == oldItem.reviewId
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}