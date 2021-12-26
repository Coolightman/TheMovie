package com.coolightman.themovie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.FavoriteDbModel

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteDbModel)

    @Query("select * from favoritedbmodel order by favoriteDate")
    suspend fun getFavorites(): List<FavoriteDbModel>

    @Query("delete from favoritedbmodel")
    suspend fun clearTable()
}