package com.coolightman.themovie.presentation.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.coolightman.themovie.R
import com.coolightman.themovie.presentation.fragment.MoviesFavoriteFragment
import com.coolightman.themovie.presentation.fragment.MoviesPopularFragment
import com.coolightman.themovie.presentation.fragment.MoviesTop250Fragment

class MyFragmentPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    companion object {
        private val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
        )
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesPopularFragment.newInstance()
            1 -> MoviesTop250Fragment.newInstance()
            2 -> MoviesFavoriteFragment.newInstance()
            else -> throw RuntimeException("Illegal Pager Adapter number $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 3
}