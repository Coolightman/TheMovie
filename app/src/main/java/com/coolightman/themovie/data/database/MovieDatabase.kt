package com.coolightman.themovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coolightman.themovie.data.database.dao.*
import com.coolightman.themovie.data.database.dbModel.*

@Database(
    version = 1,
    entities = [
        FactsDbModel::class,
        FramesDbModel::class,
        MovieDbModel::class,
        ShortFavoriteDbModel::class,
        ShortMovieDbModel::class,
        SimilarMoviesDbModel::class,
        VideosDbModel::class
    ]
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun factsDao(): FactsDao
    abstract fun framesDao(): FramesDao
    abstract fun movieDao(): MovieDao
    abstract fun shortFavoriteDao(): ShortFavoriteDao
    abstract fun shortMovieDao(): ShortMovieDao
    abstract fun similarMoviesDao(): SimilarMoviesDao
    abstract fun videosDao(): VideosDao

    companion object {
        private var database: MovieDatabase? = null
        private const val DATABASE_NAME = "TheMovie.db"

        fun getDatabase(context: Context): MovieDatabase {
            synchronized(MovieDatabase::class) {
                database?.let { return it }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                database = instance
                return instance
            }
        }
    }
}