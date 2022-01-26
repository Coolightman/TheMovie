package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.MovieSearchDao
import com.coolightman.themovie.data.mapper.MovieSearchMapper
import com.coolightman.themovie.data.network.ApiServiceOld
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.domain.repository.MovieSearchRepository
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val searchDao: MovieSearchDao,
    private val apiServiceOld: ApiServiceOld,
    private val mapper: MovieSearchMapper
) : MovieSearchRepository {

    override fun getMovieSearchList(): LiveData<List<MovieSearch>> =
        Transformations.map(searchDao.getAll()) {
            it?.let { mapper.mapDbModelToEntityList(it) } ?: emptyList()
        }

    override suspend fun searchMovies(keywords: String) {
        if (keywords.isNotEmpty()) {
            searchDao.clearTable()
            val result = apiServiceOld.searchMovies(keyword = keywords)
            val dbModel = mapper.mapDtoToDbModelsList(result)
            searchDao.insertList(dbModel)
        }
    }
}