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
        return dto.movies
            .filter { it.rating != "null" && it.year != "null" }
            .map { mapDtoToDbModel(it) }
    }

    private fun mapDtoToDbModel(dto: MovieSearchDto) = MovieSearchDbModel(
        movieId = dto.movieId,
        nameRu = dto.nameRu,
        nameEn = dto.nameEn,
        type = dto.type,
        year = dto.year!!.toInt(),
        filmLength = dto.filmLength,
        rating = dto.rating,
        posterUrl = dto.posterUrl,
        posterUrlPreview = dto.posterUrlPreview
    )


    fun mapDbModelToEntityList(dbModelList: List<MovieSearchDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }

    private fun mapDbModelToEntity(dbModel: MovieSearchDbModel) = MovieSearch(
        movieId = dbModel.movieId,
        rating = dbModel.rating,
        posterPreview = dbModel.posterUrlPreview,
        nameEn = dbModel.nameEn,
        nameRu = dbModel.nameRu,
        releaseDate = dbModel.year.toString(),
        duration = dbModel.filmLength
    )
}