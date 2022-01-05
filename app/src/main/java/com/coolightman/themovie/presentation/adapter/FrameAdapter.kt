package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FrameItemBinding
import com.coolightman.themovie.domain.entity.Frame

class FrameAdapter(
    private val clickListener: (Frame) -> Unit
) : ListAdapter<Frame, FrameViewHolder>(FrameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val binding = FrameItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FrameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        val frame = getItem(position)
        frame?.let {
            with(holder.binding) {
                setImage(this, frame)
                root.setOnClickListener { clickListener(frame) }
            }
        }
    }

    private fun setImage(binding: FrameItemBinding, frame: Frame) {
        Glide.with(binding.root.context)
            .load(frame.image)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.imgFrame)
    }
}