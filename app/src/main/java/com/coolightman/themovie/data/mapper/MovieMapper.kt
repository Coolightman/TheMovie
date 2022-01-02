package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.*
import com.coolightman.themovie.data.network.dto.CountryDto
import com.coolightman.themovie.data.network.dto.GenreDto
import com.coolightman.themovie.data.network.dto.MovieDto
import com.coolightman.themovie.data.network.dto.MoviesPageDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.Country
import com.coolightman.themovie.domain.entity.Genre
import com.coolightman.themovie.domain.entity.Movie
import javax.inject.Inject

@ApplicationScope
class MovieMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: MovieDto) = MovieDbModel(
        movieId = dto.movieId,
        nameOriginal = dto.nameOriginal,
        nameRu = dto.nameRu,
        slogan = dto.slogan,
        rating = dto.ratingKinopoisk,
        ratingCount = dto.ratingKinopoiskVoteCount,
        ratingAwait = dto.ratingAwait,
        ratingAwaitCount = dto.ratingAwaitCount,
        posterPreview = dto.posterUrlPreview,
        poster = dto.posterUrl,
        isFavorite = false,
        releaseDate = dto.year.toString(),
        duration = formatLength(dto.filmLength),
        description = dto.description,
        genres = dto.genres.map { genresDtoToDbModel(it) },
        countries = dto.countries.map { countriesDtoToDbModel(it) },
        webUrl = dto.webUrl
    )

    fun mapDbModelToEntity(dbModel: MovieDbModel) = Movie(
        movieId = dbModel.movieId,
        nameOriginal = dbModel.nameOriginal,
        nameRu = dbModel.nameRu,
        slogan = dbModel.slogan,
        rating = dbModel.rating,
        ratingCount = dbModel.ratingCount.toString(),
        ratingAwait = dbModel.ratingAwait,
        ratingAwaitCount = dbModel.ratingAwaitCount.toString(),
        posterPreview = dbModel.posterPreview,
        poster = dbModel.poster,
        isFavorite = dbModel.isFavorite,
        releaseDate = dbModel.releaseDate,
        duration = dbModel.duration,
        description = dbModel.description,
        genres = dbModel.genres.map { genresDbModelToEntity(it) },
        countries = dbModel.countries.map { countriesDbModelToEntity(it) },
        webUrl = dbModel.webUrl
    )

    fun mapMovieDbModelToFavoriteDbModel(dbModel: MovieDbModel) = FavoriteDbModel(
        movieId = dbModel.movieId,
        nameOriginal = dbModel.nameOriginal,
        nameRu = dbModel.nameRu,
        slogan = dbModel.slogan,
        rating = dbModel.rating,
        ratingCount = dbModel.ratingCount,
        ratingAwait = dbModel.ratingAwait,
        ratingAwaitCount = dbModel.ratingAwaitCount,
        posterPreview = dbModel.posterPreview,
        poster = dbModel.poster,
        favoriteDate = getStamp(),
        releaseDate = dbModel.releaseDate,
        duration = dbModel.duration,
        description = dbModel.description,
        genres = dbModel.genres,
        countries = dbModel.countries,
        webUrl = dbModel.webUrl
    )

    private fun getStamp(): Long {
        return System.currentTimeMillis()
    }

    fun mapFavoriteDbModelToEntity(dbModel: FavoriteDbModel) = Movie(
        movieId = dbModel.movieId,
        nameOriginal = dbModel.nameOriginal,
        nameRu = dbModel.nameRu,
        slogan = dbModel.slogan,
        rating = dbModel.rating,
        ratingCount = dbModel.ratingCount.toString(),
        ratingAwait = dbModel.ratingAwait,
        ratingAwaitCount = dbModel.ratingAwaitCount.toString(),
        posterPreview = dbModel.posterPreview,
        poster = dbModel.poster,
        isFavorite = true,
        releaseDate = dbModel.releaseDate,
        duration = dbModel.duration,
        description = dbModel.description,
        genres = dbModel.genres.map { genresDbModelToEntity(it) },
        countries = dbModel.countries.map { countriesDbModelToEntity(it) },
        webUrl = dbModel.webUrl
    )

    fun mapMoviesPageDtoToDbModel(dto: MoviesPageDto): List<ShortMovieDbModel>{
        return dto.movies.map { ShortMovieMapper().mapDtoToDbModel(it) }
    }

    private fun formatLength(length: Int?): String {
        length?.let {
            val hours = length / 60
            val minutes = length % 60
            return String.format("%02d:%02d", hours, minutes)
        }
        return ""
    }

    private fun genresDtoToDbModel(dto: GenreDto) = GenreDbModel(dto.genre ?: "")
    private fun countriesDtoToDbModel(dto: CountryDto) = CountryDbModel(dto.country ?: "")

    private fun genresDbModelToEntity(dbModel: GenreDbModel) = Genre(dbModel.name)
    private fun countriesDbModelToEntity(dbModel: CountryDbModel) = Country(dbModel.name)
}