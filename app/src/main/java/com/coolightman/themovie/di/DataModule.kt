package com.coolightman.themovie.di

import android.content.Context
import androidx.room.Room
import com.coolightman.themovie.data.database.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    private const val DB_NAME = "MovieDatabase.db"

    @Provides
    fun provideMovieDatabase(context: Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        DB_NAME
    ).build()

    @Provides
    fun provideFactsDao(db: MovieDatabase) = db.factsDao()

    @Provides
    fun provideFavoriteDao(db: MovieDatabase) = db.favoriteDao()

    @Provides
    fun provideFramesDao(db: MovieDatabase) = db.framesDao()

    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @Provides
    fun provideShortMovieDao(db: MovieDatabase) = db.shortMovieDao()

    @Provides
    fun provideSimilarsDao(db: MovieDatabase) = db.similarsDao()

    @Provides
    fun provideVideosDao(db: MovieDatabase) = db.videosDao()
}