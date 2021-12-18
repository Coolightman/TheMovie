package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coolightman.themovie.domain.entity.Country
import com.coolightman.themovie.domain.entity.Genre

@Entity
data class MovieDbModel(
    @PrimaryKey
    val movieId: Long,
    val titleOriginal: String?,
    val titleRu: String?,
    var slogan: String?,
    val rating: String?,
    val ratingCount: String?,
    val posterPreview: String?,
    val poster: String?,
    var isFavorite: Boolean,
    var favoriteDate: Long?,
    val releaseDate: String?,
    val duration: String?,
    val description: String?,
    val genres: List<GenreDbModel>,
    val countries: List<CountryDbModel>,
    val webUrl: String?,
    val topPopularPlace: Int,
    val top250Place: Int
)
