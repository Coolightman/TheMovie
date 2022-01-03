package com.coolightman.themovie.data.repository

import android.util.Log
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.repository.PageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val shortMovieDao: ShortMovieDao,
    private val apiService: ApiService,
    private val movieMapper: MovieMapper
) : PageRepository {

    override suspend fun loadPopularNextPage() {
        withContext(Dispatchers.IO) {
            val currentPage = getPopularCurrentPageNumber()
            if (currentPage < TOP_POPULAR_TOTAL_PAGES) {
                loadPagePopularMovies(currentPage + 1)
            }
        }
    }

    private suspend fun getPopularCurrentPageNumber() =
        shortMovieDao.getPopularCount() / AMOUNT_MOVIES_PER_PAGE

    private suspend fun loadPagePopularMovies(pageNumber: Int) {
        val pageDto = apiService.loadPagePopularMovies(pageNumber)
        val dbList = movieMapper.mapMoviesPageDtoToDbModel(pageDto)
        val updatedList = addTopPopularNumber(dbList, pageNumber)
        Log.d("MYLOG_loadPopPage", updatedList.toString())
        shortMovieDao.insertList(updatedList)
    }

    override suspend fun loadTop250NextPage() {
        withContext(Dispatchers.IO) {
            val currentPage = getTop250CurrentPageNumber()
            if (currentPage < TOP_250_TOTAL_PAGES) {
                loadPageTop250Movies(currentPage + 1)
            }
        }
    }

    private suspend fun getTop250CurrentPageNumber() =
        shortMovieDao.getTop250Count() / AMOUNT_MOVIES_PER_PAGE

    private suspend fun loadPageTop250Movies(pageNumber: Int) {
        val pageDto = apiService.loadPageTop250Movies(pageNumber)
        val dbList = movieMapper.mapMoviesPageDtoToDbModel(pageDto)
        val updatedList = addTop250Number(dbList, pageNumber)
        Log.d("MYLOG_loadTop250Page", updatedList.toString())
        shortMovieDao.insertList(updatedList)
    }

    private suspend fun addTopPopularNumber(
        dbList: List<ShortMovieDbModel>,
        pageNumber: Int
    ): List<ShortMovieDbModel> {
        for (movie in dbList) {
            movie.topPopularPlace = dbList.indexOf(movie) + 1 + (pageNumber - 1) * 20
            movie.top250Place = getTop250PlaceFromDb(movie.movieId)
        }
        return dbList
    }

    private suspend fun getTop250PlaceFromDb(movieId: Long): Int {
        val movieDbModel = shortMovieDao.getShortMovie(movieId)
        return movieDbModel?.top250Place ?: 0
    }

    private suspend fun getTopPopularPlaceFromDb(movieId: Long): Int {
        val movieDbModel = shortMovieDao.getShortMovie(movieId)
        return movieDbModel?.topPopularPlace ?: 0
    }

    private suspend fun addTop250Number(
        dbList: List<ShortMovieDbModel>,
        pageNumber: Int
    ): List<ShortMovieDbModel> {
        for (movie in dbList) {
            movie.top250Place = dbList.indexOf(movie) + 1 + (pageNumber - 1) * 20
            movie.topPopularPlace = getTopPopularPlaceFromDb(movie.movieId)
        }
        return dbList
    }

    companion object {
        private const val TOP_POPULAR_TOTAL_PAGES = 5
        private const val TOP_250_TOTAL_PAGES = 13
        private const val AMOUNT_MOVIES_PER_PAGE = 20
    }
}