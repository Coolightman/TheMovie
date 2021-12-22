package com.coolightman.themovie.di

import com.coolightman.themovie.data.repository.*
import com.coolightman.themovie.domain.repository.*
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindFactRepository(impl: FactRepositoryImpl): FactRepository

    @ApplicationScope
    @Binds
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @ApplicationScope
    @Binds
    fun bindFrameRepository(impl: FrameRepositoryImpl): FrameRepository

    @ApplicationScope
    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @ApplicationScope
    @Binds
    fun bindPageRepository(impl: PageRepositoryImpl): PageRepository

    @ApplicationScope
    @Binds
    fun bindSimilarRepository(impl: SimilarRepositoryImpl): SimilarRepository

    @ApplicationScope
    @Binds
    fun bindVideoRepository(impl: VideoRepositoryImpl): VideoRepository
}