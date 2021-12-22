package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.FactsDbModel

@Dao
interface FactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacts(facts: FactsDbModel)

    @Query("select * from factsdbmodel where movieId = :movieId")
    fun getFactsLiveData(movieId: Long): LiveData<FactsDbModel>

    @Query("select exists(select * from factsdbmodel where movieId = :movieId)")
    suspend fun exists(movieId: Long): Boolean

    @Query("delete from factsdbmodel")
    suspend fun clearTable()
}