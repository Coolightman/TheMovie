package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.PersonDbModel

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: PersonDbModel)

    @Query("select * from persondbmodel where personId = :personId")
    fun getPerson(personId: Long): LiveData<PersonDbModel>

    @Query("select exists(select * from persondbmodel where personId = :personId)")
    suspend fun exists(personId: Long): Boolean

    @Query("delete from persondbmodel")
    suspend fun clearTable()
}