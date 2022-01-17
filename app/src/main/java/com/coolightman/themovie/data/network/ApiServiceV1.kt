package com.coolightman.themovie.data.network

import com.coolightman.themovie.data.network.dto.PersonDto
import com.coolightman.themovie.data.network.dto.ReviewsDto
import com.coolightman.themovie.data.network.dto.StaffDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceV1 {

    companion object {
        private const val PARAM_PAGE = "page"
        private const val PARAM_FILM_ID = "filmId"
        private const val PARAM_ID = "id"
        private const val DEFAULT_PAGE = 1
    }

    @GET("reviews")
    suspend fun loadReviews(
        @Query(PARAM_FILM_ID) movieId: Long,
        @Query(PARAM_PAGE) page: Int = DEFAULT_PAGE
    ): ReviewsDto

    @GET("staff")
    suspend fun loadStaff(@Query(PARAM_FILM_ID) movieId: Long): List<StaffDto>

    @GET("staff/{id}")
    suspend fun loadPerson(@Path(PARAM_ID) personId: Long): PersonDto
}