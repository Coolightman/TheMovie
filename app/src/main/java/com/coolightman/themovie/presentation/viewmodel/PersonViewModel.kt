package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.entity.Person
import com.coolightman.themovie.domain.usecase.GetPersonUseCase
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase
) : ViewModel() {

    fun getPerson(personId: Long): LiveData<Person> = getPersonUseCase(personId)
}