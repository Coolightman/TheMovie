package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.ReviewItemBinding
import com.coolightman.themovie.domain.entity.Review
import com.coolightman.themovie.domain.entity.ReviewType
import com.coolightman.themovie.util.CardColor
import com.coolightman.themovie.util.TextFormat

class ReviewAdapter(
    private val clickListener: (Int) -> Unit
) : ListAdapter<Review, ReviewViewHolder>(ReviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        review?.let {
            with(holder.binding) {
                setReview(this, review)
                root.setOnClickListener { clickListener(position) }
            }
        }
    }

    private fun setReview(binding: ReviewItemBinding, review: Review) {
        checkTitle(review.title, binding)
        hideAuthor(binding)
        setReviewDescription(review.description, binding)
        setReviewColor(review.type, binding)
    }

    private fun setReviewColor(type: ReviewType, binding: ReviewItemBinding) {
        val context = binding.root.context
        val card = binding.cvSingleReview
        CardColor.setCardColor(card, type, context)
    }

    private fun setReviewDescription(revDescription: String, binding: ReviewItemBinding) {
        val context = binding.root.context
        val description = TextFormat.cutTextSize(
            revDescription,
            MAX_REVIEW_DESCRIPTION_SIZE
        ) + context.resources.getString(R.string.tv_read_more)
        binding.tvReviewDescription.text = description
    }

    private fun hideAuthor(binding: ReviewItemBinding) {
        binding.tvReviewAuthor.visibility = GONE
    }

    private fun checkTitle(title: String, binding: ReviewItemBinding) {
        if (title.isNotEmpty()) {
            binding.tvReviewTitle.text = title
            binding.tvReviewTitle.visibility = VISIBLE
        } else {
            binding.tvReviewTitle.visibility = GONE
        }
    }

    companion object {
        private const val MAX_REVIEW_DESCRIPTION_SIZE = 200
    }
}