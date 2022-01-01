package com.coolightman.themovie.data.database

import androidx.room.Database
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
        FavoriteDbModel::class,
        ShortMovieDbModel::class,
        SimilarsDbModel::class,
        VideosDbModel::class
    ]
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun factsDao(): FactsDao
    abstract fun framesDao(): FramesDao
    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shortMovieDao(): ShortMovieDao
    abstract fun similarsDao(): SimilarsDao
    abstract fun videosDao(): VideosDao
}