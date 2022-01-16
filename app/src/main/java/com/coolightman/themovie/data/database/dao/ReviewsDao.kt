package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.FactsDbModel
import com.coolightman.themovie.data.database.dbModel.ReviewsDbModel

@Dao
interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: ReviewsDbModel)

    @Query("select * from reviewsdbmodel where movieId = :movieId")
    fun getReviews(movieId: Long): LiveData<ReviewsDbModel>

    @Query("select exists(select * from reviewsdbmodel where movieId = :movieId)")
    suspend fun exists(movieId: Long): Boolean

    @Query("delete from reviewsdbmodel")
    suspend fun clearTable()
}