package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.FavoriteDao
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.mapper.ShortMovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun clearPopularMovies() {
        withContext(Dispatchers.IO) {
            val populars = shortMovieDao.getPopularsList()
            val justPopulars = getJustPopulars(populars)
            deleteJustPopulars(justPopulars)

            val crossWithTop250 = getCrossWithTop250(populars)
            shortMovieDao.insertList(crossWithTop250)
        }
    }

    private suspend fun deleteJustPopulars(justPopulars: List<ShortMovieDbModel>) {
        val idList = justPopulars.map { movie -> movie.movieId }
        shortMovieDao.deleteList(idList)
    }

    private fun getCrossWithTop250(populars: List<ShortMovieDbModel>): List<ShortMovieDbModel> {
        val crossWithTop250 = populars.filter { movie -> movie.top250Place != 0 }
        for (movie in crossWithTop250) {
            movie.topPopularPlace = 0
        }
        return crossWithTop250
    }

    private fun getJustPopulars(populars: List<ShortMovieDbModel>) =
        populars.filter { movie -> movie.top250Place == 0 }

    override suspend fun clearTop250Movies() {
        withContext(Dispatchers.IO) {
            val top250 = shortMovieDao.getTop250List()
            val justTop250 = getJustTop250(top250)
            deleteJustTop250(justTop250)

            val crossWithPopulars = getCrossWithPopulars(top250)
            shortMovieDao.insertList(crossWithPopulars)
        }
    }

    private suspend fun deleteJustTop250(justTop250: List<ShortMovieDbModel>) {
        val idList = justTop250.map { movie -> movie.movieId }
        shortMovieDao.deleteList(idList)
    }

    private fun getCrossWithPopulars(top250: List<ShortMovieDbModel>): List<ShortMovieDbModel> {
        val crossWithPopulars = top250.filter { movie -> movie.topPopularPlace != 0 }
        for (movie in crossWithPopulars) {
            movie.top250Place = 0
        }
        return crossWithPopulars
    }

    private fun getJustTop250(top250: List<ShortMovieDbModel>)=
        top250.filter { movie -> movie.topPopularPlace == 0 }

    override suspend fun getMovieInfo(movieId: Long): LiveData<Movie> {
        val exists = movieDao.exists(movieId)
        if (!exists) {
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