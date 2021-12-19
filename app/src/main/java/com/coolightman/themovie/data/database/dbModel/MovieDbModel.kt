package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coolightman.themovie.domain.entity.Country
import com.coolightman.themovie.domain.entity.Genre

@Entity
data class MovieDbModel(
    @PrimaryKey
    val movieId: Long,
    val nameOriginal: String?,
    val nameRu: String?,
    val slogan: String?,
    val rating: String?,
    val ratingCount: Int?,
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
    var topPopularPlace: Int = 0,
    var top250Place: Int = 0
)
