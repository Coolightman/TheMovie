package com.coolightman.themovie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coolightman.themovie.presentation.fragment.MainFragment
import com.coolightman.themovie.presentation.viewmodel.MainViewModel
import com.coolightman.themovie.presentation.viewmodel.MovieDetailViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    @Binds
    fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}