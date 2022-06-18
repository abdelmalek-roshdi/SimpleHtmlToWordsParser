package com.abdelmalek.simplehtmltowordsparser.domain.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.sorting.WordsSorting

interface SortWordsUseCase {
    fun sortWords(words: List<Word>, sortingOrder: WordsSorting): List<Word>
}