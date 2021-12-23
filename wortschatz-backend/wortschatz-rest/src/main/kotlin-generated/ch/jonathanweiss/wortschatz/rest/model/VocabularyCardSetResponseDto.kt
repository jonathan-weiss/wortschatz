package ch.jonathanweiss.wortschatz.rest.model

import java.util.Objects
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardDto
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param vocabularyCards 
 */
data class VocabularyCardSetResponseDto(

    @field:JsonProperty("vocabularyCards", required = true) val vocabularyCards: kotlin.collections.List<VocabularyCardDto>
) {

}

