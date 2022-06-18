package com.abdelmalek.simplehtmltowordsparser.data.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.sorting.WordsSorting
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.SortWordsUseCase

class SortWordsUseCaseImpl : SortWordsUseCase {
    override fun sortWords(words: List<Word>, sortingOrder: WordsSorting): List<Word> {
        return if (sortingOrder == WordsSorting.ASC) {
            words.sortedBy {
                it.word
            }
        } else {
            words.sortedByDescending {
                it.word
            }
        }
    }
}