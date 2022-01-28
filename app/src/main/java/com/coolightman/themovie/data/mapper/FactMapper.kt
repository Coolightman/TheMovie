package com.coolightman.themovie.data.mapper

import androidx.core.text.HtmlCompat
import com.coolightman.themovie.data.database.dbModel.FactDbModel
import com.coolightman.themovie.data.database.dbModel.FactsDbModel
import com.coolightman.themovie.data.network.dto.FactDto
import com.coolightman.themovie.data.network.dto.FactsDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.Fact
import javax.inject.Inject

@ApplicationScope
class FactMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: FactsDto, movieId: Long) = FactsDbModel(
        movieId = movieId,
        items = dto.items
            .filter { it.text != null && it.text!!.length <= 300 && it.spoiler != null }
            .map { mapFrameDtoToDbModel(it) }
    )

    private fun mapFrameDtoToDbModel(dto: FactDto) = FactDbModel(
        text = cleanText(dto.text!!),
        spoiler = dto.spoiler!!
    )

    private fun cleanText(text: String): String {
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

    fun mapDbModelToListOfFact(dbModel: FactsDbModel): List<Fact> {
        return dbModel.items.map { Fact(text = it.text, spoiler = it.spoiler) }
    }
}