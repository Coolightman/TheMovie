package com.coolightman.themovie.data.repository

import android.util.Log
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.repository.PageRepository
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val shortMovieDao: ShortMovieDao,
    private val apiService: ApiService
) : PageRepository {

    companion object {
        private const val TOP_POPULAR_TOTAL_PAGES = 5
        private const val TOP_250_TOTAL_PAGES = 13
        private const val AMOUNT_MOVIES_PER_PAGE = 20
        private const val TOP_250_MAX_MOVIES_COUNT = 250
    }

    override suspend fun loadPopularNextPage() {
        val currentPage = shortMovieDao.getPopularCount() / AMOUNT_MOVIES_PER_PAGE
        if (currentPage < TOP_POPULAR_TOTAL_PAGES) {
            loadPagePopularMovies(currentPage + 1)
        }
    }

    private suspend fun loadPagePopularMovies(pageNumber: Int) {
        val pageDto = apiService.loadPagePopularMovies(pageNumber)
        val dbList = MovieMapper().mapMoviesPageDtoToDbModel(pageDto)
        val updatedList = addTopPopularNumbers(dbList, pageNumber)
        Log.d("MYLOG_loadPopPage", updatedList.toString())
        shortMovieDao.insertList(updatedList)
    }

    override suspend fun loadTop250NextPage() {
        val dbTopMoviesCount = shortMovieDao.getTop250Count()
        val currentPage = dbTopMoviesCount / AMOUNT_MOVIES_PER_PAGE
        if (currentPage < TOP_250_TOTAL_PAGES && dbTopMoviesCount != TOP_250_MAX_MOVIES_COUNT) {
            loadPageTop250Movies(currentPage + 1)
        }
    }

    private suspend fun loadPageTop250Movies(pageNumber: Int) {
        val pageDto = apiService.loadPageTop250Movies(pageNumber)
        val dbList = MovieMapper().mapMoviesPageDtoToDbModel(pageDto)
        val updatedList = addTop250Numbers(dbList, pageNumber)
        Log.d("MYLOG_loadTop250Page", updatedList.toString())
        shortMovieDao.insertList(updatedList)
    }

    private fun addTopPopularNumbers(
        dbList: List<ShortMovieDbModel>,
        pageNumber: Int
    ): List<ShortMovieDbModel> {
        for (movie in dbList) {
            movie.topPopularPlace = dbList.indexOf(movie) + 1 + (pageNumber - 1) * 20
        }
        return dbList
    }

    private fun addTop250Numbers(
        dbList: List<ShortMovieDbModel>,
        pageNumber: Int
    ): List<ShortMovieDbModel> {
        for (movie in dbList) {
            movie.top250Place = dbList.indexOf(movie) + 1 + (pageNumber - 1) * 20
        }
        return dbList
    }
}