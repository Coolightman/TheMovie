package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FrameItemBinding
import com.coolightman.themovie.databinding.VideoItemBinding
import com.coolightman.themovie.domain.entity.Frame
import com.coolightman.themovie.domain.entity.Video
import com.coolightman.themovie.util.TextFormat.cutTextSize

class VideoAdapter(private val clickListener: (String) -> Unit) :
    ListAdapter<Video, VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        video?.let {
            with(holder.binding) {
                setVideoPreview(this, video.previewUrl)
                val videoName = cutVideoName(video.name)
                tvVideoName.text = videoName
                root.setOnClickListener { clickListener(video.url) }
            }
        }
    }

    private fun cutVideoName(name: String): String {
        return cutTextSize(name, MAX_NAME_SIZE)
    }

    private fun setVideoPreview(binding: VideoItemBinding, videoPreview: String) {
        Glide.with(binding.root.context)
            .load(videoPreview)
            .placeholder(R.drawable.video_placeholder)
            .into(binding.imgVideoPreview)
    }

    companion object{
        private const val MAX_NAME_SIZE = 25
    }
}