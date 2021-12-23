package ch.jonathanweiss.wortschatz.api.command

import ch.jonathanweiss.wortschatz.api.VocabularyCard

data class CreateVocabularyCardCommand(val vocabularyCard: VocabularyCard)
