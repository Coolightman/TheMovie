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
                setNameEn(this, staff.nameEn)
                setNameRu(this, staff.nameRu)
                setAlias(this, staff.alias)
                setProfession(this, staff.professionText)
                root.setOnClickListener { clickListener(staff) }
            }
        }
    }

    private fun setNameEn(binding: StaffItemBinding, nameEn: String) {
        if (nameEn.isNotEmpty()){
            binding.tvNameEn.text = nameEn
            binding.tvNameEn.visibility = VISIBLE
        } else{
            binding.tvNameEn.visibility = GONE
        }
    }

    private fun setNameRu(binding: StaffItemBinding, nameRu: String) {
        if (nameRu.isNotEmpty()){
            binding.tvNameRu.text = nameRu
            binding.tvNameRu.visibility = VISIBLE
        } else{
            binding.tvNameRu.visibility = GONE
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

    private fun setProfession(binding: StaffItemBinding, professionText: String) {
        if (professionText.isNotEmpty()){
            binding.tvProfession.text = professionText
            binding.tvProfession.visibility = VISIBLE
        } else{
            binding.tvProfession.visibility = GONE
        }
    }

    private fun setStaffPoster(binding: StaffItemBinding, staff: Staff) {
        Glide.with(binding.root.context)
            .load(staff.posterUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.imgPreview)
    }
}