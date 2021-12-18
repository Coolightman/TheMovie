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
    fun insertVideos(videos: VideosDbModel)

    @Query("select * from videosdbmodel where movieId = :movieId")
    fun getVideosLiveData(movieId: Long): LiveData<VideosDbModel>

    @Query("delete from videosdbmodel")
    fun clearTable()
}