package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.StaffDao
import com.coolightman.themovie.data.mapper.StaffMapper
import com.coolightman.themovie.data.network.ApiServiceV1
import com.coolightman.themovie.domain.entity.Staff
import com.coolightman.themovie.domain.repository.StaffRepository
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val staffDao: StaffDao,
    private val apiServiceV1: ApiServiceV1,
    private val mapper: StaffMapper
) : StaffRepository {

    override fun getMovieStaff(movieId: Long): LiveData<List<Staff>> =
        Transformations.map(staffDao.getStaff(movieId)) {
            it?.let { mapper.mapDbModelListToEntityList(it) } ?: emptyList()
        }

    override suspend fun fetchMovieStaff(movieId: Long) {
        if (!staffDao.exists(movieId)) {
            loadStaffFromApi(movieId)
        }
    }

    override suspend fun refreshMovieStaff(movieId: Long) {
        loadStaffFromApi(movieId)
    }

    private suspend fun loadStaffFromApi(movieId: Long) {
        val staffDtoList = apiServiceV1.loadStaff(movieId)
        val staffDbModel = mapper.mapDtoListToDbModelList(staffDtoList, movieId)
        staffDao.insert(staffDbModel)
    }
}