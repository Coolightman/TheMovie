package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.StaffPreviewItemBinding
import com.coolightman.themovie.domain.entity.Staff

class StaffPreviewAdapter(private val clickListener: (Staff) -> Unit) :
    ListAdapter<Staff, StaffPreviewViewHolder>(StaffDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffPreviewViewHolder {
        val binding = StaffPreviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffPreviewViewHolder, position: Int) {
        val staff = getItem(position)
        staff?.let {
            with(holder.binding) {
                setStaffPreview(this, staff)
                root.setOnClickListener { clickListener(staff) }
            }
        }
    }

    private fun setStaffPreview(binding: StaffPreviewItemBinding, staff: Staff) {
        Glide.with(binding.root.context)
            .load(staff.posterUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.imgPreview)
    }
}