package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.SimilarsDbModel

@Dao
interface SimilarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(similars: SimilarsDbModel)

    @Query("select * from similarsdbmodel where movieId = :movieId")
    fun getSimilars(movieId: Long): LiveData<SimilarsDbModel>

    @Query("select exists(select * from similarsdbmodel where movieId = :movieId)")
    suspend fun exists(movieId: Long): Boolean

    @Query("delete from similarsdbmodel")
    suspend fun clearTable()
}