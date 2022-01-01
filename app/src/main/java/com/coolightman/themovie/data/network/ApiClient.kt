package com.coolightman.themovie.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"
    private const val HEADER_ACCEPT_NAME = "accept"
    private const val HEADER_ACCEPT_VALUE = "application/json"
    private const val HEADER_API_KEY_NAME = "X-API-KEY"
    private const val HEADER_API_KEY_VALUE = "655063ab-c3b3-40fd-86ce-7868c8fc3b57"

    private fun getRetrofit(): Retrofit {
        val client = getHttpClient().build()
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun getHttpClient(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header(HEADER_ACCEPT_NAME, HEADER_ACCEPT_VALUE)
                    .header(HEADER_API_KEY_NAME, HEADER_API_KEY_VALUE)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })

        return httpClient
    }

    fun getApiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }
}