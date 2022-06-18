package com.abdelmalek.simplehtmltowordsparser.data.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.SearchWordsUseCase

class SearchWordsUseCaseImpl: SearchWordsUseCase {
    override fun searchWords(query: String, words: List<Word>): List<Word> {
        return words.filter { it.word.contains(query, true) }
    }
}