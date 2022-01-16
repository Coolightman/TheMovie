package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.ReviewsDao
import com.coolightman.themovie.data.mapper.ReviewMapper
import com.coolightman.themovie.data.network.ApiServiceV1
import com.coolightman.themovie.domain.entity.Review
import com.coolightman.themovie.domain.repository.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewsDao: ReviewsDao,
    private val apiServiceV1: ApiServiceV1,
    private val mapper: ReviewMapper
) : ReviewRepository {

    override fun getMovieReviews(movieId: Long): LiveData<List<Review>> = liveData {
        withContext(Dispatchers.IO) {
            if (!reviewsDao.exists(movieId)) {
                loadReviewsFromApi(movieId)
            }
            val list = Transformations.map(reviewsDao.getReviews(movieId)) {
                mapper.mapDbModelToListOfReview(it)
            }
            emitSource(list)
        }
    }

    private suspend fun loadReviewsFromApi(movieId: Long) {
        val reviewsDto = apiServiceV1.loadReviews(movieId)
        val reviewsDbModel = mapper.mapDtoToDbModel(reviewsDto)
        reviewsDao.insert(reviewsDbModel)
    }
}