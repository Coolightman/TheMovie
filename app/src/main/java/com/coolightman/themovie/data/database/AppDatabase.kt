package com.coolightman.themovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coolightman.themovie.data.database.dao.*
import com.coolightman.themovie.data.database.dbModel.*

@Database(
    version = 3,
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
abstract class AppDatabase : RoomDatabase() {
    abstract fun factsDao(): FactsDao
    abstract fun framesDao(): FramesDao
    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shortMovieDao(): ShortMovieDao
    abstract fun similarsDao(): SimilarsDao
    abstract fun videosDao(): VideosDao

    companion object {
        private const val DB_NAME = "MovieDatabase.db"

        fun getMovieDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}