package ch.jonathanweiss.wortschatz.query

import ch.jonathanweiss.wortschatz.api.VocabularyCard
import ch.jonathanweiss.wortschatz.api.VocabularyCardId
import ch.jonathanweiss.wortschatz.api.event.VocabularyCardCreatedEvent
import ch.jonathanweiss.wortschatz.api.query.FetchVocabularyCardSetQuery
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Service
import java.util.*

@Service
@ProcessingGroup("vocabulary-cards")
class VocabularyCardLearningQuery(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection") private val queryUpdateEmitter: QueryUpdateEmitter
    )
{

    private val vocabularyCards: MutableList<VocabularyCard> = mutableListOf(
        VocabularyCard(createVocabularyCardIdFrom("hello"), "hello","Hallo"),
        VocabularyCard(createVocabularyCardIdFrom("morning"), "morning","Morgen"),
        VocabularyCard(createVocabularyCardIdFrom("evening"), "evening","Abend"),
        VocabularyCard(createVocabularyCardIdFrom("sun"), "sun","Sonne"),
        VocabularyCard(createVocabularyCardIdFrom("moon"), "moon","Mond"),
        VocabularyCard(createVocabularyCardIdFrom("bye"), "bye","Tschüss")
    )

    @QueryHandler
    fun handle(query: FetchVocabularyCardSetQuery): List<VocabularyCard> {
        return vocabularyCards
    }

    @EventHandler
    fun handle(event: VocabularyCardCreatedEvent) {
        this.vocabularyCards.add(event.vocabularyCard)
    }

    private fun createVocabularyCardIdFrom(name: String): VocabularyCardId {
        return VocabularyCardId(UUID.nameUUIDFromBytes(name.toByteArray()))
    }
}
