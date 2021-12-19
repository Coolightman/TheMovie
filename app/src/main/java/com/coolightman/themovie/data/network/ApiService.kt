package com.coolightman.themovie.data.network

import com.coolightman.themovie.data.network.dto.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val HEADER_ACCEPT_NAME = "accept"
        private const val HEADER_ACCEPT_VALUE = "application/json"
        private const val HEADER_API_NAME = "X-API-KEY"
        private const val HEADER_API_VALUE = "655063ab-c3b3-40fd-86ce-7868c8fc3b57"
        private const val PARAM_TYPE = "type"
        private const val TYPE_VALUE_POPULAR = "TOP_100_POPULAR_FILMS"
        private const val TYPE_VALUE_TOP_250 = "TOP_250_BEST_FILMS"
        private const val PARAM_PAGE = "page"
        private const val PARAM_MOVIE_ID = "id"
        private const val PARAM_FRAMES_TYPE = "STILL"
        private const val DEFAULT_PAGE = 1

        private const val headerAccept = "$HEADER_ACCEPT_NAME: $HEADER_ACCEPT_VALUE"
        private const val headerKey = "$HEADER_API_NAME: $HEADER_API_VALUE"
    }

    @Headers(headerAccept, headerKey)
    @GET("top")
    suspend fun loadPageOfPopularMovies(
        @Query(PARAM_PAGE) page: Int,
        @Query(PARAM_TYPE) type: String = TYPE_VALUE_POPULAR
    ): MoviesPageDto

    @Headers(headerAccept, headerKey)
    @GET("top")
    suspend fun loadPageOfTop250Movies(
        @Query(PARAM_PAGE) page: Int,
        @Query(PARAM_TYPE) type: String = TYPE_VALUE_TOP_250
    ): MoviesPageDto

    @Headers(headerAccept, headerKey)
    @GET("{id}")
    suspend fun loadMovie(@Path(PARAM_MOVIE_ID) movieId: Long): MovieDto

    @Headers(headerAccept, headerKey)
    @GET("{id}/facts")
    suspend fun loadFacts(@Path(PARAM_MOVIE_ID) movieId: Long): FactsDto

    @Headers(headerAccept, headerKey)
    @GET("{id}/images")
    suspend fun loadFrames(
        @Path(PARAM_MOVIE_ID) movieId: Long,
        @Query(PARAM_PAGE) page: Int = DEFAULT_PAGE,
        @Query(PARAM_TYPE) type: String = PARAM_FRAMES_TYPE
    ): FramesDto

    @Headers(headerAccept, headerKey)
    @GET("{id}/videos")
    suspend fun loadVideos(@Path(PARAM_MOVIE_ID) movieId: Long): VideosDto

    @Headers(headerAccept, headerKey)
    @GET("{id}/similars")
    suspend fun loadSimilars(@Path(PARAM_MOVIE_ID) movieId: Long): SimilarsDto

}