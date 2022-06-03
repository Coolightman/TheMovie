package com.coolightman.themovie.di

import com.coolightman.themovie.data.network.*
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideApi(): ApiService {
        return ApiClient.getApiService()
    }

    @Provides
    fun provideApiOld(): ApiServiceOld {
        return ApiClientOld.getApiService()
    }

    @Provides
    fun provideApiV1(): ApiServiceV1 {
        return ApiClientV1.getApiService()
    }
}