package ch.jonathanweiss.wortschatz.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class VocabularyTrainingController {

    @GetMapping("/vocabulary-card-set")
    fun index(): VocabularyCardSetData {
        return VocabularyCardSetData(listOf(
            VocabularyCardData("hello","Hallo"),
            VocabularyCardData("bye","Tschüss")
        ))
    }
}

