package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.MovieSearchDbModel
import com.coolightman.themovie.data.network.dto.MovieSearchDto
import com.coolightman.themovie.data.network.dto.SearchMoviesDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.MovieSearch
import javax.inject.Inject

@ApplicationScope
class MovieSearchMapper @Inject constructor() {

    fun mapDtoToDbModelsList(dto: SearchMoviesDto): List<MovieSearchDbModel> {
        return dto.movies.map { mapDtoToDbModel(it) }
    }

    private fun mapDtoToDbModel(dto: MovieSearchDto) = MovieSearchDbModel(
        movieId = dto.movieId,
        nameRu = dto.nameRu,
        nameEn = dto.nameEn,
        type = dto.type,
        year = dto.year,
        filmLength = dto.filmLength,
        rating = dto.rating,
        posterUrl = dto.posterUrl,
        posterUrlPreview = dto.posterUrlPreview
    )


    fun mapDbModelToEntity(dbModel: MovieSearchDbModel) = MovieSearch(
        movieId = dbModel.movieId,
        rating = dbModel.rating,
        posterPreview = dbModel.posterUrlPreview,
        nameEn = dbModel.nameEn,
        nameRu = dbModel.nameRu,
        releaseDate = dbModel.year,
        duration = dbModel.filmLength
    )
}