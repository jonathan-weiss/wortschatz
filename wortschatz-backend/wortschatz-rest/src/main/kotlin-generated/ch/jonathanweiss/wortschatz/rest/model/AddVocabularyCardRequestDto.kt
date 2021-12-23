package ch.jonathanweiss.wortschatz.rest.model

import java.util.Objects
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardDto
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param vocabularyCardToAdd 
 */
data class AddVocabularyCardRequestDto(

    @field:JsonProperty("vocabularyCardToAdd", required = true) val vocabularyCardToAdd: VocabularyCardDto
) {

}

