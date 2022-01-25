package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.coolightman.themovie.databinding.FilmographyItemBinding
import com.coolightman.themovie.domain.entity.PersonFilm

class FilmographyAdapter :
    ListAdapter<PersonFilm, FilmographyViewHolder>(PersonFilmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyViewHolder {
        val binding = FilmographyItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmographyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val fact = getItem(position)
        fact?.let {
            with(holder.binding) {
                setNameRu(this, fact.nameRu)
                setNameEn(this, fact.nameEn)
                setAlias(this, fact.description)
            }
        }
    }

    private fun setNameRu(binding: FilmographyItemBinding, nameRu: String?) {
        if (nameRu != null) {
            binding.tvNameRu.text = nameRu
            binding.tvNameRu.visibility = VISIBLE
        } else {
            binding.tvNameRu.visibility = GONE
        }
    }

    private fun setNameEn(binding: FilmographyItemBinding, nameEn: String?) {
        if (nameEn != null) {
            binding.tvNameEn.text = nameEn
            binding.tvNameEn.visibility = VISIBLE
        } else {
            binding.tvNameEn.visibility = GONE
        }
    }

    private fun setAlias(binding: FilmographyItemBinding, description: String?) {
        if (description != null) {
            binding.tvDescription.text = description
            binding.tvDescription.visibility = VISIBLE
        } else {
            binding.tvDescription.visibility = GONE
        }
    }


}