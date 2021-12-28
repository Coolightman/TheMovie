package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
import com.coolightman.themovie.data.database.dbModel.SimilarsDbModel
import com.coolightman.themovie.data.network.dto.ShortMovieDto
import com.coolightman.themovie.data.network.dto.SimilarsDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.ShortMovie
import javax.inject.Inject

@ApplicationScope
class SimilarMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: SimilarsDto, movieId: Long) = SimilarsDbModel(
        movieId = movieId,
        items = dto.items.map { mapShortMovieDtoToDbModel(it) }
    )

    private fun mapShortMovieDtoToDbModel(dto: ShortMovieDto) = ShortMovieDbModel(
        movieId = dto.movieId,
        rating = dto.rating,
        posterPreview = dto.posterPreview
    )

    fun mapDbModelToListShortMovie(dbModel: SimilarsDbModel): List<ShortMovie> {
        return dbModel.items.map { mapShortMovieDbModelToEntity(it) }
    }

    private fun mapShortMovieDbModelToEntity(dbModel: ShortMovieDbModel) = ShortMovie(
        movieId = dbModel.movieId,
        rating = dbModel.rating,
        posterPreview = dbModel.posterPreview,
        isFavorite = dbModel.isFavorite
    )
}