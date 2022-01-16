package com.coolightman.themovie.di

import com.coolightman.themovie.data.network.*
import dagger.Module
import dagger.Provides

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

    @ApplicationScope
    @Provides
    fun provideApiV1(): ApiServiceV1 {
        return ApiClientV1.getApiService()
    }
}