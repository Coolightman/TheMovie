package com.coolightman.themovie.di

import com.coolightman.themovie.data.repository.*
import com.coolightman.themovie.domain.repository.*
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindFactRepository(impl: FactRepositoryImpl): FactRepository

    @Binds
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    fun bindFrameRepository(impl: FrameRepositoryImpl): FrameRepository

    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindPageRepository(impl: PageRepositoryImpl): PageRepository

    @Binds
    fun bindSimilarRepository(impl: SimilarRepositoryImpl): SimilarRepository

    @Binds
    fun bindVideoRepository(impl: VideoRepositoryImpl): VideoRepository
}