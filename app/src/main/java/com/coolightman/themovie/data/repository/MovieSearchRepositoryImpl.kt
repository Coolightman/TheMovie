package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.MovieSearchDao
import com.coolightman.themovie.data.mapper.MovieSearchMapper
import com.coolightman.themovie.data.network.ApiServiceOld
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.domain.repository.MovieSearchRepository
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val searchDao: MovieSearchDao,
    private val apiServiceOld: ApiServiceOld,
    private val movieSearchMapper: MovieSearchMapper
) : MovieSearchRepository {

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