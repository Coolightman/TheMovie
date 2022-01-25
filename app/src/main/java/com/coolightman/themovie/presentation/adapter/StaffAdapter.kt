package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.StaffItemBinding
import com.coolightman.themovie.domain.entity.Staff

class StaffAdapter(private val clickListener: (Staff) -> Unit) :
    ListAdapter<Staff, StaffViewHolder>(StaffDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = StaffItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staff = getItem(position)
        staff?.let {
            with(holder.binding) {
                setStaffPoster(this, staff)
                setAlias(this, staff.alias)
                root.setOnClickListener { clickListener(staff) }
            }
        }
    }

    private fun setAlias(binding: StaffItemBinding, alias: String) {
        if (alias.isNotEmpty()){
            val text = "\"$alias\""
            binding.tvAlias.text = text
            binding.tvAlias.visibility = VISIBLE
        } else{
            binding.tvAlias.visibility = GONE
        }
    }

    private fun setStaffPoster(binding: StaffItemBinding, staff: Staff) {
        Glide.with(binding.root.context)
            .load(staff.posterUrl)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(binding.imgPreview)
    }
}