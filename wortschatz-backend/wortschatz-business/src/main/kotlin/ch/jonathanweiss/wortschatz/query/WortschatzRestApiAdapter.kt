package ch.jonathanweiss.wortschatz.query

import ch.jonathanweiss.wortschatz.api.VocabularyCard
import ch.jonathanweiss.wortschatz.api.query.FetchVocabularyCardSetQuery
import org.axonframework.messaging.responsetypes.ResponseTypes

import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.springframework.stereotype.Service



@Service
class WortschatzRestApiAdapter(private val queryBus: QueryBus) {

    fun fetchVocabularyCardSetQuery(): List<VocabularyCard> {
        val query = GenericQueryMessage(
            FetchVocabularyCardSetQuery(),
            ResponseTypes.multipleInstancesOf(
                VocabularyCard::class.java
            )
        )
        return queryBus.query(query).get().payload
    }
}
