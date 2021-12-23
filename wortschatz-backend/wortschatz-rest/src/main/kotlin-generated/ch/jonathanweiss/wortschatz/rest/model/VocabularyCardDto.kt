package ch.jonathanweiss.wortschatz.rest.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param id 
 * @param foreignWord 
 * @param validationWord 
 */
data class VocabularyCardDto(

    @field:JsonProperty("id", required = true) val id: java.util.UUID,

    @field:JsonProperty("foreignWord", required = true) val foreignWord: kotlin.String,

    @field:JsonProperty("validationWord", required = true) val validationWord: kotlin.String
) {

}

