package com.coolightman.themovie.di

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.presentation.viewmodel.*
import dagger.Binds
import dagger.Module
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

    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    @Binds
    fun bindGalleryViewModel(viewModel: GalleryViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AllFactsViewModel::class)
    @Binds
    fun bindAllFactsViewModel(viewModel: AllFactsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchMovieViewModel::class)
    @Binds
    fun bindSearchMovieViewModel(viewModel: SearchMovieViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    @Binds
    fun bindReviewMovieViewModel(viewModel: ReviewViewModel): ViewModel
}