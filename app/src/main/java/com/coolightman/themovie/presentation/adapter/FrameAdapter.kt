package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FrameItemBinding
import com.coolightman.themovie.domain.entity.Frame

class FrameAdapter(private val clickListener: (Int, AppCompatImageView) -> Unit) :
    ListAdapter<Frame, FrameViewHolder>(FrameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val binding = FrameItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FrameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        val frame = getItem(position)
        frame?.let {
            with(holder.binding) {
                setFramePreview(this, frame)
                val bind = imgFrame
                root.setOnClickListener { clickListener(position, bind) }
            }
        }
    }

    private fun setFramePreview(binding: FrameItemBinding, frame: Frame) {
        Glide.with(binding.root.context)
            .load(frame.imagePreview)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.imgFrame)
    }
}