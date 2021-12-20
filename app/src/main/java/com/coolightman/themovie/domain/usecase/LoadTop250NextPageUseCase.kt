package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.PageRepository

class LoadTop250NextPageUseCase(
    private val repository: PageRepository
) {
    operator fun invoke() = repository.loadTop250NextPage()
}