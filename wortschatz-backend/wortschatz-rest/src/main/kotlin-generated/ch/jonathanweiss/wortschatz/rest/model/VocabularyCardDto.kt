package ch.jonathanweiss.wortschatz.rest.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param foreignWord 
 * @param validationWord 
 */
data class VocabularyCardDto(

    @field:JsonProperty("foreignWord", required = true) val foreignWord: kotlin.String,

    @field:JsonProperty("validationWord", required = true) val validationWord: kotlin.String
) {

}

