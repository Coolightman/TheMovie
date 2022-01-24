package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Person

interface PersonRepository {

    fun getPerson(personId: Long): LiveData<Person>
}