package ch.jonathanweiss.wortschatz.rest

import org.mapstruct.factory.Mappers

object Mappers {
    val vocabularyCardMapper: VocabularyCardMapper = Mappers.getMapper(VocabularyCardMapper::class.java)

}
