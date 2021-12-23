package ch.jonathanweiss.wortschatz.rest.api

import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardSetResponseDto

interface WortschatzApiService {

    fun vocabularyCardSet(limit: kotlin.Int?): VocabularyCardSetResponseDto
}
