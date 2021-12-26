package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.FavoriteDao
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.mapper.ShortMovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val shortMovieDao: ShortMovieDao,
    private val favoriteDao: FavoriteDao,
    private val apiService: ApiService
) : MovieRepository {

    override fun getPopularMovies(): LiveData<List<ShortMovie>> {
        return Transformations.map(shortMovieDao.getPopulars()) { list ->
            list.map { ShortMovieMapper().mapDbModelToEntity(it) }
        }
    }

    override fun getTop250Movies(): LiveData<List<ShortMovie>> {
        return Transformations.map(shortMovieDao.getTop250()) { list ->
            list.map { ShortMovieMapper().mapDbModelToEntity(it) }
        }
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(favoriteDao.getFavorites()) { list ->
            list.map { MovieMapper().mapFavoriteDbModelToEntity(it) }
        }
    }

    override suspend fun getMovieInfo(movieId: Long): LiveData<Movie> {
        val exists = movieDao.exists(movieId)
        if (!exists){
            loadMovieFromApi(movieId)
        }
        return getMovieFromDb(movieId)
    }

    private suspend fun loadMovieFromApi(movieId: Long) {
        val movieDto = apiService.loadMovie(movieId)
        val movieDbModel = MovieMapper().mapDtoToDbModel(movieDto)
        movieDao.insert(movieDbModel)
    }

    private fun getMovieFromDb(movieId: Long): LiveData<Movie> {
        return Transformations.map(movieDao.getMovie(movieId)) {
            MovieMapper().mapDbModelToEntity(it)
        }
    }
}