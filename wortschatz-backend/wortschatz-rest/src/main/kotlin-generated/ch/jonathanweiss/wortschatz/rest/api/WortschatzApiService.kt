package ch.jonathanweiss.wortschatz.rest.api

import ch.jonathanweiss.wortschatz.rest.model.AddVocabularyCardRequestDto
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardSetResponseDto

interface WortschatzApiService {

    fun addVocabularyCard(addVocabularyCardRequestDto: AddVocabularyCardRequestDto): Unit

    fun vocabularyCardSet(limit: kotlin.Int?): VocabularyCardSetResponseDto
}
