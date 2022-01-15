package com.coolightman.themovie.di

import com.coolightman.themovie.data.network.ApiClient
import com.coolightman.themovie.data.network.ApiClientOld
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.data.network.ApiServiceOld
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

    @ApplicationScope
    @Provides
    fun provideApiOld(): ApiServiceOld {
        return ApiClientOld.getApiService()
    }
}