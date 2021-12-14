package com.coolightman.themovie.di

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.presentation.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    @Binds
    fun bindExampleViewModel(impl: MovieViewModel): ViewModel
}