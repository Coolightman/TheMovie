package com.coolightman.themovie.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientV1 {

    private const val API_URL = "https://kinopoiskapiunofficial.tech/api/v1/"
    private const val HEADER_ACCEPT_NAME = "accept"
    private const val HEADER_ACCEPT_VALUE = "application/json"
    private const val HEADER_API_KEY_NAME = "X-API-KEY"
    private const val HEADER_API_KEY_VALUE = "655063ab-c3b3-40fd-86ce-7868c8fc3b57"

    fun getApiService(): ApiServiceV1 {
        return getRetrofit().create(ApiServiceV1::class.java)
    }

    private fun getRetrofit(): Retrofit {
        val client = getHttpClient()
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                getChainForHeaders(chain)
            })
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    private fun getChainForHeaders(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header(HEADER_ACCEPT_NAME, HEADER_ACCEPT_VALUE)
            .header(HEADER_API_KEY_NAME, HEADER_API_KEY_VALUE)
            .method(original.method, original.body)
            .build()
        return chain.proceed(request)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
}