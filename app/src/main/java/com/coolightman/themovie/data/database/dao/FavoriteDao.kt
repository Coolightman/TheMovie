package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.FavoriteDbModel

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteDbModel)

    @Query("select * from favoritedbmodel order by favoriteDate")
    fun getFavorites(): LiveData<List<FavoriteDbModel>>

    @Query("delete from favoritedbmodel")
    fun clearTable()
}