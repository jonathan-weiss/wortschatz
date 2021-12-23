package ch.jonathanweiss.wortschatz.rest

import ch.jonathanweiss.wortschatz.api.VocabularyCard
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardDto
import org.mapstruct.Mapper

@Mapper
interface VocabularyCardMapper {

    fun mapToDto(entity: VocabularyCard): VocabularyCardDto
}
