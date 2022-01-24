package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.FavoriteDao
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.data.database.dao.MovieSearchDao
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.mapper.MovieSearchMapper
import com.coolightman.themovie.data.mapper.ShortMovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.data.network.ApiServiceOld
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val searchDao: MovieSearchDao,
    private val apiService: ApiService,
    private val apiServiceOld: ApiServiceOld,
    private val movieMapper: MovieMapper,
    private val movieSearchMapper: MovieSearchMapper
) : MovieRepository {

    override fun getMovie(movieId: Long): LiveData<Movie> = liveData {
        if (!movieDao.exists(movieId)) {
            loadMovieFromApi(movieId)
        }
        val movie = Transformations.map(movieDao.getMovie(movieId)) {
            movieMapper.mapDbModelToEntity(it)
        }
        emitSource(movie)
    }

    private suspend fun loadMovieFromApi(movieId: Long) {
        val movieDto = apiService.loadMovie(movieId)
        val movieDbModel = movieMapper.mapDtoToDbModel(movieDto)
        movieDao.insert(movieDbModel)
    }

    override fun getMovieSearchList(): LiveData<List<MovieSearch>> = liveData {
        val dbModel = searchDao.getAll()
        val search = Transformations.map(dbModel) { list ->
            list.map { movieSearchMapper.mapDbModelToEntity(it) }
        }
        emitSource(search)
    }

    override suspend fun searchMovies(keywords: String) {
        if (keywords.isNotEmpty()) {
            searchDao.clearTable()
            val result = apiServiceOld.searchMovies(keyword = keywords)
            val dbModel = movieSearchMapper.mapDtoToDbModelsList(result)
            searchDao.insertList(dbModel)
        }
    }
}