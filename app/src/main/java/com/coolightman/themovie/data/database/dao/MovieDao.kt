package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.MovieDbModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDbModel)

    @Query("select * from moviedbmodel where movieId = :movieId")
    fun getMovie(movieId: Long): MovieDbModel?

    @Query("select * from moviedbmodel where movieId = :movieId")
    fun getMovieLiveData(movieId: Long): LiveData<MovieDbModel>

    @Query("delete from moviedbmodel")
    fun clearTable()
}