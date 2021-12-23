package ch.jonathanweiss.wortschatz.rest

import ch.jonathanweiss.wortschatz.query.WortschatzRestApiAdapter
import ch.jonathanweiss.wortschatz.rest.api.WortschatzApiService
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardDto
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardSetResponseDto
import org.springframework.stereotype.Service


@Service
class WortschatzApiServiceImpl(private val wortschatzRestApiAdapter: WortschatzRestApiAdapter): WortschatzApiService{

    override fun vocabularyCardSet(limit: Int?): VocabularyCardSetResponseDto {
        return VocabularyCardSetResponseDto(
             wortschatzRestApiAdapter.fetchVocabularyCardSetQuery().map(Mappers.vocabularyCardMapper::mapToDto)
        )

//        return VocabularyCardSetResponseDto(listOf(
//            VocabularyCardDto("hello","Hallo"),
//            VocabularyCardDto("morning","Morgen"),
//            VocabularyCardDto("evening","Abend"),
//            VocabularyCardDto("sun","Sonne"),
//            VocabularyCardDto("moon","Mond"),
//            VocabularyCardDto("bye","Tschüss")
//        ))


    }

}

