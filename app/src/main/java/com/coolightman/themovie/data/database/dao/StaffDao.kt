package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.FactsDbModel
import com.coolightman.themovie.data.database.dbModel.StaffDbModel

@Dao
interface StaffDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(staff: List<StaffDbModel>)

    @Query("select * from staffdbmodel where movieId = :movieId order by importanceNumber")
    fun getStaff(movieId: Long): LiveData<List<StaffDbModel>>

    @Query("select exists(select * from staffdbmodel where movieId = :movieId)")
    suspend fun exists(movieId: Long): Boolean

    @Query("delete from staffdbmodel")
    suspend fun clearTable()
}