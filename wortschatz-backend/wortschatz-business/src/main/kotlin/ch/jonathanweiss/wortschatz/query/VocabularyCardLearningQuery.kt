package ch.jonathanweiss.wortschatz.query

import ch.jonathanweiss.wortschatz.api.VocabularyCard
import ch.jonathanweiss.wortschatz.api.query.FetchVocabularyCardSetQuery
import org.axonframework.config.ProcessingGroup
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Service

@Service
@ProcessingGroup("vocabulary-cards")
class VocabularyCardLearningQuery(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection") private val queryUpdateEmitter: QueryUpdateEmitter
    ) {

    @QueryHandler
    fun handle(query: FetchVocabularyCardSetQuery): List<VocabularyCard> {
        return listOf(
            VocabularyCard("hello","Hallo"),
            VocabularyCard("morning","Morgen"),
            VocabularyCard("evening","Abend"),
            VocabularyCard("sun","Sonne"),
            VocabularyCard("moon","Mond"),
            VocabularyCard("bye","Tschüss")
        )


    }
}
