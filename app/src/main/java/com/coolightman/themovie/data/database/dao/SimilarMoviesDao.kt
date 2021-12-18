package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.SimilarMoviesDbModel

@Dao
interface SimilarMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSimilars(similarMovies: SimilarMoviesDbModel)

    @Query("select * from similarmoviesdbmodel where movieId = :movieId")
    fun getSimilarMoviesLiveData(movieId: Long): LiveData<SimilarMoviesDbModel>

    @Query("delete from similarmoviesdbmodel")
    fun clearTable()
}