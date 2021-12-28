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

    override suspend fun clearPopularMovies() {
        withContext(Dispatchers.IO) {
            val popularsDb = shortMovieDao.getPopularsList()
            val justPopularsList = getJustPopulars(popularsDb)
            deleteJustPopulars(justPopularsList)

            val crossWithTop250List = getCrossWithTop250(popularsDb)
            shortMovieDao.insertList(crossWithTop250List)
        }
    }

    private suspend fun deleteJustPopulars(justPopularsList: List<ShortMovieDbModel>) {
        val idList = justPopularsList.map { movie -> movie.movieId }
        shortMovieDao.deleteList(idList)
    }

    private fun getCrossWithTop250(popularsDb: List<ShortMovieDbModel>): List<ShortMovieDbModel> {
        val crossWithTop250List = popularsDb.filter { movie -> movie.top250Place != 0 }
        for (movie in crossWithTop250List) {
            movie.topPopularPlace = 0
        }
        return crossWithTop250List
    }

    private fun getJustPopulars(popularsDb: List<ShortMovieDbModel>) =
        popularsDb.filter { movie -> movie.top250Place == 0 }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(favoriteDao.getFavorites()) { list ->
            list.map { MovieMapper().mapFavoriteDbModelToEntity(it) }
        }
    }

    override suspend fun clearTop250Movies() {
        withContext(Dispatchers.IO) {
            val top250Db = shortMovieDao.getTop250List()
            val justTop250List = getJustTop250(top250Db)
            deleteJustTop250(justTop250List)

            val crossWithPopularList = getCrossWithPopulars(top250Db)
            shortMovieDao.insertList(crossWithPopularList)
        }
    }

    private suspend fun deleteJustTop250(justTop250List: List<ShortMovieDbModel>) {
        val idList = justTop250List.map { movie -> movie.movieId }
        shortMovieDao.deleteList(idList)
    }

    private fun getCrossWithPopulars(top250Db: List<ShortMovieDbModel>): List<ShortMovieDbModel> {
        val crossWithPopularsList = top250Db.filter { movie -> movie.topPopularPlace != 0 }
        for (movie in crossWithPopularsList) {
            movie.top250Place = 0
        }
        return crossWithPopularsList
    }

    private fun getJustTop250(top250Db: List<ShortMovieDbModel>)=
        top250Db.filter { movie -> movie.topPopularPlace == 0 }

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