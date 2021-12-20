package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.PageRepository

class LoadPopularNextPageUseCase(
    private val repository: PageRepository
) {
    operator fun invoke() = repository.loadPopularNextPage()
}