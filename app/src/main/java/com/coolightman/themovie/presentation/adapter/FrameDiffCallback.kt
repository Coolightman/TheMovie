package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.Frame
import com.coolightman.themovie.domain.entity.Movie

class FrameDiffCallback: DiffUtil.ItemCallback<Frame>() {
    override fun areItemsTheSame(oldItem: Frame, newItem: Frame): Boolean {
        return oldItem.imagePreview == oldItem.imagePreview
    }

    override fun areContentsTheSame(oldItem: Frame, newItem: Frame): Boolean {
        return oldItem == newItem
    }
}