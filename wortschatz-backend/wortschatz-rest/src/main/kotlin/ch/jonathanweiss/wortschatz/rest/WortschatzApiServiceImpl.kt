package ch.jonathanweiss.wortschatz.rest

import ch.jonathanweiss.wortschatz.restadapter.WortschatzRestApiAdapter
import ch.jonathanweiss.wortschatz.rest.api.WortschatzApiService
import ch.jonathanweiss.wortschatz.rest.model.AddVocabularyCardRequestDto
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardSetResponseDto
import org.springframework.stereotype.Service


@Service
class WortschatzApiServiceImpl(private val wortschatzRestApiAdapter: WortschatzRestApiAdapter): WortschatzApiService{

    override fun vocabularyCardSet(limit: Int?): VocabularyCardSetResponseDto {
        return VocabularyCardSetResponseDto(
             wortschatzRestApiAdapter.fetchVocabularyCardSetQuery().map(Mappers.vocabularyCardMapper::mapToDto)
        )
    }

    override fun addVocabularyCard(addVocabularyCardRequestDto: AddVocabularyCardRequestDto) {
        wortschatzRestApiAdapter.addNewVocabularyCard(Mappers.vocabularyCardMapper.mapToBo(addVocabularyCardRequestDto.vocabularyCardToAdd))
    }
}

