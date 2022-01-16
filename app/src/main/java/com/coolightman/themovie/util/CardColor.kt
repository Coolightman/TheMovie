package com.coolightman.themovie.util

import android.content.Context
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.coolightman.themovie.R
import com.coolightman.themovie.domain.entity.ReviewType

object CardColor {

    fun setCardColor(view: CardView, reviewType: ReviewType, context: Context) {
        when (reviewType) {
            ReviewType.POSITIVE -> view.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.grayCardBackgroundPositive
                )
            )
            ReviewType.NEUTRAL -> view.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.grayCardBackgroundNeutral
                )
            )
            ReviewType.NEGATIVE -> view.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.grayCardBackgroundNegative
                )
            )
        }

    }
}