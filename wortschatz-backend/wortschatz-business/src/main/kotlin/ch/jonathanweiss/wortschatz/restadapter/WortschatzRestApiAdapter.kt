package ch.jonathanweiss.wortschatz.restadapter

import ch.jonathanweiss.wortschatz.api.VocabularyCard
import ch.jonathanweiss.wortschatz.api.command.CreateVocabularyCardCommand
import ch.jonathanweiss.wortschatz.api.query.FetchVocabularyCardSetQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes

import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Service
import kotlin.reflect.KClass


@Service
class WortschatzRestApiAdapter(private val queryGateway: QueryGateway, private val commandGateway: CommandGateway) {

    fun fetchVocabularyCardSetQuery(): List<VocabularyCard> {
        return queryGateway.query(FetchVocabularyCardSetQuery(), multipleInstanceOf(VocabularyCard::class)).get()
    }

    fun addNewVocabularyCard(vocabularyCard: VocabularyCard) {
        val command = CreateVocabularyCardCommand(vocabularyCard)
        commandGateway.sendAndWait<Unit>(command)
    }


    private fun <T: Any> multipleInstanceOf(clazz: KClass<T>): ResponseType<List<T>> {
        return ResponseTypes.multipleInstancesOf(
            clazz.java
        )
    }

    private fun <T: Any> singleInstanceOf(clazz: KClass<T>): ResponseType<T> {
        return ResponseTypes.instanceOf(clazz.java)
    }



}
