package com.coolightman.themovie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.StaffDao
import com.coolightman.themovie.data.mapper.StaffMapper
import com.coolightman.themovie.data.network.ApiServiceV1
import com.coolightman.themovie.domain.entity.Staff
import com.coolightman.themovie.domain.repository.StaffRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val staffDao: StaffDao,
    private val apiServiceV1: ApiServiceV1,
    private val mapper: StaffMapper
) : StaffRepository {

    override fun getMovieStaff(movieId: Long): LiveData<List<Staff>> = liveData {
        withContext(Dispatchers.IO) {
            loadStaffFromApi(movieId)
            val list = Transformations.map(staffDao.getStaff(movieId)) {
                it?.let {
                    mapper.mapDbModelListToEntityList(it)
                }
            }
            emitSource(list)
        }
    }

    private suspend fun loadStaffFromApi(movieId: Long) {
        try {
            val staffDtoList = apiServiceV1.loadStaff(movieId)
            val staffDbModel = mapper.mapDtoListToDbModelList(staffDtoList, movieId)
            staffDao.insert(staffDbModel)
        } catch (e: Exception) {
            Log.e("LoadStaffFromApi", "Bad request staff in movie $movieId")
        }
    }
}