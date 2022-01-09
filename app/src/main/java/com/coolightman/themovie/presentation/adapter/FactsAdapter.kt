package com.coolightman.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.coolightman.themovie.databinding.FactItemBinding
import com.coolightman.themovie.domain.entity.Fact

class FactsAdapter : ListAdapter<Fact, FactViewHolder>(FactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = FactItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = getItem(position)
        fact?.let {
            with(holder.binding) {
                setFact(this, fact)
            }
        }
    }

    private fun setFact(binding: FactItemBinding, fact: Fact) {
        checkSpoiler(fact, binding)
        binding.tvFact.text = fact.text
    }

    private fun checkSpoiler(fact: Fact, binding: FactItemBinding) {
        if (!fact.spoiler) {
            binding.tvFactWarning.visibility = GONE
        } else{
            binding.tvFactWarning.visibility = VISIBLE
        }
    }
}