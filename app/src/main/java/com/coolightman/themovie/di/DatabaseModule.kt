package com.coolightman.themovie.di

import android.content.Context
import androidx.room.Room
import com.coolightman.themovie.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @ApplicationScope
    @Provides
    fun provideMovieDatabase(context: Context) = AppDatabase.getMovieDatabase(context)

    @ApplicationScope
    @Provides
    fun provideFactsDao(db: AppDatabase) = db.factsDao()

    @ApplicationScope
    @Provides
    fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()

    @ApplicationScope
    @Provides
    fun provideFramesDao(db: AppDatabase) = db.framesDao()

    @ApplicationScope
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @ApplicationScope
    @Provides
    fun provideShortMovieDao(db: AppDatabase) = db.shortMovieDao()

    @ApplicationScope
    @Provides
    fun provideSimilarsDao(db: AppDatabase) = db.similarsDao()

    @ApplicationScope
    @Provides
    fun provideVideosDao(db: AppDatabase) = db.videosDao()

    @ApplicationScope
    @Provides
    fun provideMovieSearchDao(db: AppDatabase) = db.movieSearchDao()
}