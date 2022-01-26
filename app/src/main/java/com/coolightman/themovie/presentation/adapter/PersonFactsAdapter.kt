package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.coolightman.themovie.databinding.PersonFactItemBinding

class PersonFactsAdapter : ListAdapter<String, PersonFactViewHolder>(PersonFactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonFactViewHolder {
        val binding = PersonFactItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonFactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonFactViewHolder, position: Int) {
        val fact = getItem(position)
        fact?.let {
            with(holder.binding) {
                setFact(this, fact)
            }
        }
    }

    private fun setFact(binding: PersonFactItemBinding, fact: String) {
        binding.tvFact.text = fact
    }
}