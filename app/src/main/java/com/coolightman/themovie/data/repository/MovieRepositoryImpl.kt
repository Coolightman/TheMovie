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
    private val shortMovieDao: ShortMovieDao,
    private val searchDao: MovieSearchDao,
    private val favoriteDao: FavoriteDao,
    private val apiService: ApiService,
    private val apiServiceOld: ApiServiceOld,
    private val shortMovieMapper: ShortMovieMapper,
    private val movieMapper: MovieMapper,
    private val movieSearchMapper: MovieSearchMapper
) : MovieRepository {

    override fun getPopularMovies(): LiveData<List<ShortMovie>> {
        return Transformations.map(shortMovieDao.getPopulars()) { list ->
            list.map { shortMovieMapper.mapDbModelToEntity(it) }
        }
    }

    override fun getTop250Movies(): LiveData<List<ShortMovie>> {
        return Transformations.map(shortMovieDao.getTop250()) { list ->
            list.map { shortMovieMapper.mapDbModelToEntity(it) }
        }
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(favoriteDao.getFavorites()) { list ->
            list.map { movieMapper.mapFavoriteDbModelToEntity(it) }
        }
    }

    override suspend fun clearPopularMovies() {
        val populars = shortMovieDao.getPopularsList()
        val justPopulars = getJustPopulars(populars)
        val crossWithTop250 = getCrossWithTop250(populars)

        deleteJustPopulars(justPopulars)
        shortMovieDao.insertList(crossWithTop250)
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
        val top250 = shortMovieDao.getTop250List()
        val justTop250 = getJustTop250(top250)
        val crossWithPopulars = getCrossWithPopulars(top250)

        deleteJustTop250(justTop250)
        shortMovieDao.insertList(crossWithPopulars)
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

    private fun getJustTop250(top250: List<ShortMovieDbModel>) =
        top250.filter { movie -> movie.topPopularPlace == 0 }

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

    override fun getTop250Place(movieId: Long): LiveData<String> = liveData {
        try {
            val place = shortMovieDao.getTop250Place(movieId)
            emit(place.toString())
        } catch (e: Exception) {
            emit("0")
        }
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