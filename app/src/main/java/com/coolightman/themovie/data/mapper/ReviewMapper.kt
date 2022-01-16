package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.ReviewDbModel
import com.coolightman.themovie.data.database.dbModel.ReviewsDbModel
import com.coolightman.themovie.data.network.dto.ReviewDto
import com.coolightman.themovie.data.network.dto.ReviewsDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.Review
import com.coolightman.themovie.domain.entity.ReviewType
import javax.inject.Inject

@ApplicationScope
class ReviewMapper @Inject constructor() {

    companion object {
        private const val EMPTY_STRING = ""
    }

    fun mapDtoToDbModel(dto: ReviewsDto) = ReviewsDbModel(
        movieId = dto.movieId,
        items = dto.reviews
            .filter { it.reviewType != null && it.reviewData != null && it.reviewAutor != null && it.reviewDescription != null }
            .map { mapReviewDtoToDbModel(it) }
    )

    private fun mapReviewDtoToDbModel(dto: ReviewDto) = ReviewDbModel(
        reviewId = dto.reviewId,
        type = dto.reviewType!!,
        data = dto.reviewData!!,
        author = dto.reviewAutor!!,
        title = dto.reviewTitle ?: EMPTY_STRING,
        description = dto.reviewDescription!!
    )

    fun mapDbModelToListOfReview(dbModel: ReviewsDbModel): List<Review> {
        return dbModel.items.map {
            Review(
                reviewId = it.reviewId,
                type = getReviewType(it.type),
                data = it.data,
                author = it.author,
                title = it.title,
                description = it.description
            )
        }
    }

    private fun getReviewType(type: String): ReviewType {
        return when (type) {
            "POSITIVE" -> ReviewType.POSITIVE
            "NEGATIVE" -> ReviewType.NEGATIVE
            "NEUTRAL" -> ReviewType.NEUTRAL
            else -> throw RuntimeException("Wrong review type from dbModel!")
        }

    }
}