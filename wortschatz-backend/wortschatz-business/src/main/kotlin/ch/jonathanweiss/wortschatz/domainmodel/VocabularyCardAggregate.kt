package ch.jonathanweiss.wortschatz.domainmodel

import ch.jonathanweiss.wortschatz.api.VocabularyCardId
import ch.jonathanweiss.wortschatz.api.command.CreateVocabularyCardCommand
import ch.jonathanweiss.wortschatz.api.event.VocabularyCardCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle


@org.axonframework.spring.stereotype.Aggregate
class VocabularyCardAggregate @Suppress("SpringJavaInjectionPointsAutowiringInspection") @CommandHandler constructor(
    command: CreateVocabularyCardCommand
) {

    @AggregateIdentifier
    private val vocabularyCardId: VocabularyCardId
    private val foreignWord: String
    private val validationWord: String


    init {
        this.vocabularyCardId = command.vocabularyCard.vocabularyCardId
        this.foreignWord = command.vocabularyCard.foreignWord
        this.validationWord = command.vocabularyCard.validationWord

        val event = VocabularyCardCreatedEvent(command.vocabularyCard)
        AggregateLifecycle.apply(event)
    }


}
