package com.coolightman.themovie.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.data.database.dao.VideosDao
import com.coolightman.themovie.data.repository.VideoRepositoryImpl
import com.coolightman.themovie.databinding.ActivityMainBinding
import com.coolightman.themovie.domain.usecase.GetMovieVideosUseCase
import com.coolightman.themovie.presentation.adapter.SectionsPagerAdapter
import com.coolightman.themovie.presentation.viewmodel.MovieViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as App).component
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createSectionsAdapter()
        doSome()
    }

    private fun doSome() {

    }

    private fun createSectionsAdapter() {
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, lifecycle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        setTabTitles(tabs, viewPager)
    }

    private fun setTabTitles(tabs: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_text_0)
                1 -> tab.text = getString(R.string.tab_text_1)
                2 -> tab.text = getString(R.string.tab_text_2)
            }
        }.attach()
    }

}