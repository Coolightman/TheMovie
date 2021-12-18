package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimilarMoviesDbModel(
    @PrimaryKey
    val movieId: Long,
    val items: List<ShortMovieDbModel>
)
