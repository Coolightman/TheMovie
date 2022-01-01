package com.coolightman.themovie.di

import com.coolightman.themovie.data.network.ApiClient
import com.coolightman.themovie.data.network.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @ApplicationScope
    @Provides
    fun provideApi(): ApiService {
        return ApiClient.getApiService()
    }
}