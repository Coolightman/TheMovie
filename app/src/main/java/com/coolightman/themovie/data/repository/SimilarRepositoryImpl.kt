package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.data.database.dao.SimilarsDao
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.SimilarRepository
import javax.inject.Inject

class SimilarRepositoryImpl @Inject constructor(
    private val similarsDao: SimilarsDao
) : SimilarRepository {

    override fun getMovieSimilars(movieId: Long): LiveData<List<ShortMovie>> {
        TODO("Not yet implemented")
    }
}