package ch.jonathanweiss.wortschatz.api

import java.util.*

data class VocabularyCardId(override val id: UUID): IdWrapper<UUID>

data class VocabularyCard(
    val vocabularyCardId: VocabularyCardId,
    val foreignWord: String,
    val validationWord: String
) {

}


