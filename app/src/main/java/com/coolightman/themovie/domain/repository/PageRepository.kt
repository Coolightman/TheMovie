package com.coolightman.themovie.domain.repository

interface PageRepository {

    suspend fun loadPopularNextPage()
    suspend fun loadTop250NextPage()
}