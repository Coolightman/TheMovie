package com.coolightman.themovie.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null
    private const val API_URL = "google.com"//TODO

    private fun getClient(): Retrofit {
        synchronized(ApiClient::class) {
            retrofit?.let { return it }
            val instance = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit = instance
            return instance
        }
    }

    fun getMovieApiService(): MovieApiService {
        return getClient().create(MovieApiService::class.java)
    }
}
