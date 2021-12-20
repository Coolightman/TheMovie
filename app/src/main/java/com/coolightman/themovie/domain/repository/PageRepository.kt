package com.coolightman.themovie.domain.repository

interface PageRepository {

    fun loadPopularNextPage()
    fun loadTop250NextPage()
}