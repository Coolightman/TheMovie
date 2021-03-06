package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel

@Dao
interface ShortMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(shortMovies: List<ShortMovieDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortMovie(shortMovie: ShortMovieDbModel)

    @Query("select * from shortmoviedbmodel where movieId = :movieId")
    suspend fun getShortMovie(movieId: Long): ShortMovieDbModel?

    @Query("select * from shortmoviedbmodel where topPopularPlace > 0 order by topPopularPlace")
    fun getPopulars(): LiveData<List<ShortMovieDbModel>>

    @Query("select * from shortmoviedbmodel where topPopularPlace > 0 order by topPopularPlace")
    suspend fun getPopularsList(): List<ShortMovieDbModel>

    @Query("select COUNT(movieId) from shortmoviedbmodel where topPopularPlace > 0")
    suspend fun getPopularCount(): Int

    @Query("select * from shortmoviedbmodel where top250Place > 0 order by top250Place")
    fun getTop250(): LiveData<List<ShortMovieDbModel>>

    @Query("select * from shortmoviedbmodel where top250Place > 0 order by top250Place")
    suspend fun getTop250List(): List<ShortMovieDbModel>

    @Query("select COUNT(movieId) from shortmoviedbmodel where top250Place > 0")
    suspend fun getTop250Count(): Int

    @Query("select top250Place from shortmoviedbmodel where movieId = :movieId")
    suspend fun getTop250Place(movieId: Long): Int

    @Query("delete from shortmoviedbmodel")
    suspend fun clearTable()

    @Query("delete from shortmoviedbmodel where movieId in (:idList)")
    suspend fun deleteList(idList: List<Long>)
}