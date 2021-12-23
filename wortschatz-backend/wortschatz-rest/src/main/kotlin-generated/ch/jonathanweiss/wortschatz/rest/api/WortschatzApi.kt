package ch.jonathanweiss.wortschatz.rest.api

import ch.jonathanweiss.wortschatz.rest.model.VocabularyCardSetResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired


import kotlin.collections.List
import kotlin.collections.Map

@RestController
@RequestMapping("\${api.base-path:/v1}")
class WortschatzApiController(@Autowired(required = true) val service: WortschatzApiService) {


    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/wortschatz/vocabulary-card-set"],
        produces = ["application/json"]
    )
    fun vocabularyCardSet( @RequestParam(value = "limit", required = false) limit: kotlin.Int?
): ResponseEntity<VocabularyCardSetResponseDto> {
        return ResponseEntity(service.vocabularyCardSet(limit), HttpStatus.valueOf(200))
    }
}
