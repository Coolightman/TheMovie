package com.coolightman.themovie.data.network

import com.coolightman.themovie.data.network.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val PARAM_TYPE = "type"
        private const val TYPE_VALUE_POPULAR = "TOP_100_POPULAR_FILMS"
        private const val TYPE_VALUE_TOP_250 = "TOP_250_BEST_FILMS"
        private const val PARAM_PAGE = "page"
        private const val PARAM_KEYWORD = "keyword"
        private const val PARAM_MOVIE_ID = "id"
        private const val PARAM_FRAMES_TYPE = "STILL"
        private const val DEFAULT_PAGE = 1
    }

    @GET("top")
    suspend fun loadPagePopularMovies(
        @Query(PARAM_PAGE) page: Int,
        @Query(PARAM_TYPE) type: String = TYPE_VALUE_POPULAR
    ): MoviesPageDto

    @GET("top")
    suspend fun loadPageTop250Movies(
        @Query(PARAM_PAGE) page: Int,
        @Query(PARAM_TYPE) type: String = TYPE_VALUE_TOP_250
    ): MoviesPageDto

    @GET("{id}")
    suspend fun loadMovie(@Path(PARAM_MOVIE_ID) movieId: Long): MovieDto

    @GET("{id}/facts")
    suspend fun loadFacts(@Path(PARAM_MOVIE_ID) movieId: Long): FactsDto

    @GET("{id}/images")
    suspend fun loadFrames(
        @Path(PARAM_MOVIE_ID) movieId: Long,
        @Query(PARAM_PAGE) page: Int = DEFAULT_PAGE,
        @Query(PARAM_TYPE) type: String = PARAM_FRAMES_TYPE
    ): FramesDto

    @GET("{id}/videos")
    suspend fun loadVideos(@Path(PARAM_MOVIE_ID) movieId: Long): VideosDto

    @GET("{id}/similars")
    suspend fun loadSimilars(@Path(PARAM_MOVIE_ID) movieId: Long): SimilarsDto
}