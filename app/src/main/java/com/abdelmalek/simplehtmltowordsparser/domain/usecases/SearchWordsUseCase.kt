package com.abdelmalek.simplehtmltowordsparser.domain.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word

interface SearchWordsUseCase {
    fun searchWords(query: String, words: List<Word>): List<Word>
}