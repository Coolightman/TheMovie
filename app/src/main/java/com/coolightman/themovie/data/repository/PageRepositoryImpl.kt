package com.coolightman.themovie.data.repository

import android.util.Log
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.repository.PageRepository
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val shortMovieDao: ShortMovieDao,
    private val apiService: ApiService
) : PageRepository {

    override suspend fun loadPopularNextPage() {
        val page = apiService.loadPageOfPopularMovies(1)
        val dbList = MovieMapper().mapMoviesPageDtoToDbModel(page)
        for (movie in dbList) {
            movie.topPopularPlace = dbList.indexOf(movie) + 1
        }
        Log.d("MYLOG_loadPopPage", dbList.toString())
        shortMovieDao.insertList(dbList)
    }

    override suspend fun loadTop250NextPage() {
        val page = apiService.loadPageOfTop250Movies(1)
        val dbList = MovieMapper().mapMoviesPageDtoToDbModel(page)
        for (movie in dbList) {
            movie.top250Place = dbList.indexOf(movie) + 1
        }
        Log.d("MYLOG_loadTop250Page", dbList.toString())
        shortMovieDao.insertList(dbList)
    }
}