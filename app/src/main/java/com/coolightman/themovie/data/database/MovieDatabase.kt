package com.coolightman.themovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(
//    entities = [],
//    version = 1,
//    exportSchema = false
//)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        private var database: MovieDatabase? = null
        private const val DATABASE_NAME = "TheMovie.db"

        fun getDatabase(context: Context): MovieDatabase {
            synchronized(MovieDatabase::class){
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