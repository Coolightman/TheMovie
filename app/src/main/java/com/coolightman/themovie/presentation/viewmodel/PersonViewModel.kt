package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetPersonUseCase
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase
) : ViewModel() {

    private var personId: Long = 0

    val person by lazy {
        getPersonUseCase(personId)
    }

    fun setPersonId(personId: Long) {
        this.personId = personId
    }
}