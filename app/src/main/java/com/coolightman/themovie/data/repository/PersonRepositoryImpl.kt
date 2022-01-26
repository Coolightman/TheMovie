package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.PersonDao
import com.coolightman.themovie.data.mapper.PersonMapper
import com.coolightman.themovie.data.network.ApiServiceV1
import com.coolightman.themovie.domain.entity.Person
import com.coolightman.themovie.domain.repository.PersonRepository
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val personDao: PersonDao,
    private val apiServiceV1: ApiServiceV1,
    private val personMapper: PersonMapper
) : PersonRepository {

    override fun getPerson(personId: Long): LiveData<Person> = liveData {
        if (!personDao.exists(personId)) {
            loadPersonFromApi(personId)
        }
        val person = Transformations.map(personDao.getPerson(personId)) {
            personMapper.mapDbModelToEntity(it)
        }
        emitSource(person)
    }

    private suspend fun loadPersonFromApi(personId: Long) {
        val personDto = apiServiceV1.loadPerson(personId)
        val personDbModel = personMapper.mapDtoToDbModel(personDto)
        personDao.insert(personDbModel)
    }
}