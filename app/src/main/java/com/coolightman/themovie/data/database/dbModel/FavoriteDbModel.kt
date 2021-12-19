package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteDbModel(
    @PrimaryKey
    val movieId: Long,
    val titleOriginal: String?,
    val titleRu: String?,
    val slogan: String?,
    val rating: String?,
    val ratingCount: String?,
    val posterPreview: String?,
    val poster: String?,
    val isFavorite: Boolean,
    val favoriteDate: Long?,
    val releaseDate: String?,
    val duration: String?,
    val description: String?,
    val genres: List<GenreDbModel>,
    val countries: List<CountryDbModel>,
    val webUrl: String?,
    val topPopularPlace: Int,
    val top250Place: Int
)
