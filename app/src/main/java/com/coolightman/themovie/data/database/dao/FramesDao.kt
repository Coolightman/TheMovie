package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.FramesDbModel

@Dao
interface FramesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFrames(frames: FramesDbModel)

    @Query("select * from framesdbmodel where movieId = :movieId")
    fun getFramesLiveData(movieId: Long): LiveData<FramesDbModel>

    @Query("select exists(select * from framesdbmodel where movieId = :movieId)")
    fun exists(movieId: Long): Boolean

    @Query("delete from framesdbmodel")
    fun clearTable()
}