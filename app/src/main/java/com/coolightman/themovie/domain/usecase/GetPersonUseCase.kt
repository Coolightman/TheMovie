package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.PersonRepository
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    operator fun invoke(personId: Long) = repository.getPerson(personId)
}