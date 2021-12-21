package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.VideosDbModel

@Dao
interface VideosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: VideosDbModel)

    @Query("select * from videosdbmodel where movieId = :movieId")
    fun getVideosLiveData(movieId: Long): LiveData<VideosDbModel>

    @Query("select exists(select * from videosdbmodel where movieId = :movieId)")
    suspend fun exists(movieId: Long): Boolean

    @Query("delete from videosdbmodel")
    suspend fun clearTable()
}