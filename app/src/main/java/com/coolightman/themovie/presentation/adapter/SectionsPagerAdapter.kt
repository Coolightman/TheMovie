package com.coolightman.themovie.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coolightman.themovie.presentation.fragment.MoviesFavoriteFragment
import com.coolightman.themovie.presentation.fragment.MoviesPopularFragment
import com.coolightman.themovie.presentation.fragment.MoviesTop250Fragment

class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesPopularFragment.newInstance()
            1 -> MoviesTop250Fragment.newInstance()
            2 -> MoviesFavoriteFragment.newInstance()
            else -> throw RuntimeException("Illegal Pager Adapter number $position")
        }
    }
}