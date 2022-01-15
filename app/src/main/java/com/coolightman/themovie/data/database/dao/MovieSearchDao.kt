package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.MovieSearchDbModel

@Dao
interface MovieSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(movies: List<MovieSearchDbModel>)

    @Query("select * from moviesearchdbmodel order by year desc")
    fun getAll(): LiveData<List<MovieSearchDbModel>>

    @Query("delete from moviesearchdbmodel")
    suspend fun clearTable()
}