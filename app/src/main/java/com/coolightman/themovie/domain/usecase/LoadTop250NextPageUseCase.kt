package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.PageRepository
import javax.inject.Inject

class LoadTop250NextPageUseCase @Inject constructor(
    private val repository: PageRepository
) {
    suspend operator fun invoke() = repository.loadTop250NextPage()
}