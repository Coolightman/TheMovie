package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteDbModel(
    @PrimaryKey
    val movieId: Long,
    val nameOriginal: String?,
    val nameRu: String?,
    val slogan: String?,
    val rating: String?,
    val ratingCount: Int?,
    val ratingAwait: String?,
    val ratingAwaitCount: Int?,
    val ratingImdb: String?,
    val ratingImdbCount: Int?,
    val ratingCritics: String?,
    val ratingCriticsCount: Int?,
    val posterPreview: String?,
    val poster: String?,
    val favoriteDate: Long?,
    val releaseDate: String?,
    val duration: String?,
    val description: String?,
    val genres: List<GenreDbModel>,
    val countries: List<CountryDbModel>,
    val webUrl: String?,
    val ageLimits: String?
)
