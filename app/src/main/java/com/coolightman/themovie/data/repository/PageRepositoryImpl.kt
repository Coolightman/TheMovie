package com.coolightman.themovie.data.repository

import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.domain.repository.PageRepository
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val movieDao: ShortMovieDao
) : PageRepository {

    override suspend fun loadPopularNextPage() {
        TODO("Not yet implemented")
    }

    override suspend fun loadTop250NextPage() {
        TODO("Not yet implemented")
    }
}