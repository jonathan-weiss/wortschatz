package ch.jonathanweiss.wortschatz.rest

import ch.jonathanweiss.wortschatz.rest.api.WortschatzApiService
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardDto
import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardSetResponseDto
import org.springframework.stereotype.Service


@Service
class WortschatzApiServiceImpl: WortschatzApiService{

    override fun vocabularyCardSet(limit: Int?): VocabularyCardSetResponseDto {
        return VocabularyCardSetResponseDto(listOf(
            VocabularyCardDto("hello","Hallo"),
            VocabularyCardDto("bye","Tschüss")
        ))
    }

}

