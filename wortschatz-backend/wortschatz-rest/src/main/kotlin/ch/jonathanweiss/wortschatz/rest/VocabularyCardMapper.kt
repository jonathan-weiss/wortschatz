package ch.jonathanweiss.wortschatz.rest

import ch.jonathanweiss.wortschatz.api.VocabularyCard
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardDto
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface VocabularyCardMapper {

    @Mappings(
        Mapping(source = "vocabularyCardId.id", target = "id")
    )
    fun mapToDto(entity: VocabularyCard): VocabularyCardDto

    @InheritInverseConfiguration
    fun mapToBo(dto: VocabularyCardDto): VocabularyCard
}
