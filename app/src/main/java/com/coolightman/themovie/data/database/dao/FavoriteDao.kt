package com.coolightman.themovie.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolightman.themovie.data.database.dbModel.ShortFavoriteDbModel

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shortFavorite: ShortFavoriteDbModel)

    @Query("select * from shortfavoritedbmodel order by favoriteDate")
    fun getFavorites(): LiveData<List<ShortFavoriteDbModel>>

    @Query("delete from shortfavoritedbmodel")
    fun clearTable()
}