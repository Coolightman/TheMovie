package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
import com.coolightman.themovie.data.network.dto.ShortMovieDto
import com.coolightman.themovie.domain.entity.ShortMovie

class ShortMovieMapper {

    fun mapDtoToDbModel(dto: ShortMovieDto) = ShortMovieDbModel(
        movieId = dto.movieId,
        rating = dto.rating,
        posterPreview = dto.posterPreview
    )

    fun mapDbModelToEntity(dbModel: ShortMovieDbModel) = ShortMovie(
        movieId = dbModel.movieId,
        rating = dbModel.rating,
        posterPreview = dbModel.posterPreview,
        isFavorite = dbModel.isFavorite
    )
}