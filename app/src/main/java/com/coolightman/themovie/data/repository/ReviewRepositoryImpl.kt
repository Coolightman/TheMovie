package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.ReviewsDao
import com.coolightman.themovie.data.mapper.ReviewMapper
import com.coolightman.themovie.data.network.ApiServiceV1
import com.coolightman.themovie.domain.entity.Review
import com.coolightman.themovie.domain.repository.ReviewRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewsDao: ReviewsDao,
    private val apiServiceV1: ApiServiceV1,
    private val mapper: ReviewMapper
) : ReviewRepository {

    override fun getMovieReviews(movieId: Long): LiveData<List<Review>> =
        Transformations.map(reviewsDao.getReviews(movieId)) {
            it?.let { mapper.mapDbModelToListOfReview(it) } ?: emptyList()
        }

    override suspend fun fetchMovieReviews(movieId: Long) {
        if (!reviewsDao.exists(movieId)) {
            loadReviewsFromApi(movieId)
        }
    }

    private suspend fun loadReviewsFromApi(movieId: Long) {
//        Delay needed to fix http 429 from api
        delay(1000)
        val reviewsDto = apiServiceV1.loadReviews(movieId)
        val reviewsDbModel = mapper.mapDtoToDbModel(reviewsDto)
        reviewsDao.insert(reviewsDbModel)
    }
}