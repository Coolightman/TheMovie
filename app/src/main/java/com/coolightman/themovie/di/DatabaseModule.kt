package com.coolightman.themovie.di

import android.content.Context
import androidx.room.Room
import com.coolightman.themovie.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideMovieDatabase(context: Context) = AppDatabase.getMovieDatabase(context)

    @Provides
    fun provideFactsDao(db: AppDatabase) = db.factsDao()

    @Provides
    fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()

    @Provides
    fun provideFramesDao(db: AppDatabase) = db.framesDao()

    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Provides
    fun provideShortMovieDao(db: AppDatabase) = db.shortMovieDao()

    @Provides
    fun provideSimilarsDao(db: AppDatabase) = db.similarsDao()

    @Provides
    fun provideVideosDao(db: AppDatabase) = db.videosDao()

    @Provides
    fun provideMovieSearchDao(db: AppDatabase) = db.movieSearchDao()

    @Provides
    fun provideReviewsDao(db: AppDatabase) = db.reviewsDao()

    @Provides
    fun provideStaffDao(db: AppDatabase) = db.staffDao()

    @Provides
    fun providePersonDao(db: AppDatabase) = db.personDao()
}