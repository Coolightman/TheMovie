package com.coolightman.themovie.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.network.ApiClient
import com.coolightman.themovie.databinding.ActivityMainBinding
import com.coolightman.themovie.presentation.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

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
        val apiService = ApiClient.getApiService()
        CoroutineScope(Dispatchers.IO).launch {
            val movieDto = apiService.loadMovie(301)
            Log.d("TEST", movieDto.toString())
            val movieDbModel = MovieMapper().mapDtoToDbModel(movieDto)
            movieDbModel.isFavorite = true
            Log.d("TEST", movieDbModel.toString())
            val movie = MovieMapper().mapDbModelToEntity(movieDbModel)
            Log.d("TEST", movie.toString())
            val favoriteDbModel = MovieMapper().mapMovieDbModelToFavoriteDbModel(movieDbModel)
            Log.d("TEST", favoriteDbModel.toString())
            val movieFavorite = MovieMapper().mapFavoriteDbModelToEntity(favoriteDbModel)
            Log.d("TEST", movieFavorite.toString())
        }
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