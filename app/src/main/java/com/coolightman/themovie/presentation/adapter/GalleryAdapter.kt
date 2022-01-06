package com.coolightman.themovie.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.LARGE
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.GalleryFrameItemBinding
import com.coolightman.themovie.domain.entity.Frame

class GalleryAdapter : ListAdapter<Frame, GalleryFrameViewHolder>(FrameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFrameViewHolder {
        val binding = GalleryFrameItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryFrameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryFrameViewHolder, position: Int) {
        val frame = getItem(position)
        frame?.let {
            with(holder.binding) {
                setFrame(this, frame)
            }
        }
    }

    private fun setFrame(binding: GalleryFrameItemBinding, frame: Frame) {
        val context = binding.root.context
        val progressBar = CircularProgressDrawable(context)
        progressBar.setStyle(LARGE)
        progressBar.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
        progressBar.strokeWidth = 20F
        progressBar.centerRadius = 80f
        progressBar.start()

        Glide.with(binding.root.context)
            .load(frame.image)
            .placeholder(progressBar)
            .into(binding.imgGalleryFrame)
    }
}