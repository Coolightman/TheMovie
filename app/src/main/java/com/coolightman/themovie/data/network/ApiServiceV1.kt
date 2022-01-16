package com.coolightman.themovie.data.network

import com.coolightman.themovie.data.network.dto.ReviewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceV1 {

    companion object {
        private const val PARAM_PAGE = "page"
        private const val PARAM_FILM_ID = "filmId"
        private const val DEFAULT_PAGE = 1
    }

    @GET("reviews")
    suspend fun loadReviews(
        @Query(PARAM_FILM_ID) movieId: Long,
        @Query(PARAM_PAGE) page: Int = DEFAULT_PAGE
    ): ReviewsDto
}