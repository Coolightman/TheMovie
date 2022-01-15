package com.coolightman.themovie.data.network

import com.coolightman.themovie.data.network.dto.SearchMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceOld {

    companion object {
        private const val PARAM_PAGE = "page"
        private const val PARAM_KEYWORD = "keyword"
        private const val DEFAULT_PAGE = 1
    }

    @GET("search-by-keyword")
    suspend fun searchMovies(
        @Query(PARAM_KEYWORD) keyword: String,
        @Query(PARAM_PAGE) page: Int = DEFAULT_PAGE
    ): SearchMoviesDto
}