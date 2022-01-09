package com.coolightman.themovie.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coolightman.themovie.domain.entity.Fact

class FactDiffCallback : DiffUtil.ItemCallback<Fact>() {
    override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem.text == oldItem.text
    }

    override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem == newItem
    }
}