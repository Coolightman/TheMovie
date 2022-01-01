package com.coolightman.themovie.di

import android.content.Context
import androidx.room.Room
import com.coolightman.themovie.data.database.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    companion object {
        private const val DB_NAME = "MovieDatabase.db"
    }

    @ApplicationScope
    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            DB_NAME
        ).build()
    }

    @ApplicationScope
    @Provides
    fun provideFactsDao(db: MovieDatabase) = db.factsDao()

    @ApplicationScope
    @Provides
    fun provideFavoriteDao(db: MovieDatabase) = db.favoriteDao()

    @ApplicationScope
    @Provides
    fun provideFramesDao(db: MovieDatabase) = db.framesDao()

    @ApplicationScope
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @ApplicationScope
    @Provides
    fun provideShortMovieDao(db: MovieDatabase) = db.shortMovieDao()

    @ApplicationScope
    @Provides
    fun provideSimilarsDao(db: MovieDatabase) = db.similarsDao()

    @ApplicationScope
    @Provides
    fun provideVideosDao(db: MovieDatabase) = db.videosDao()
}