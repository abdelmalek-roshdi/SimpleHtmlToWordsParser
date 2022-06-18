package com.abdelmalek.simplehtmltowordsparser.domain.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult

interface GetWordsUseCase {
    fun getWordsList(callback: (WordsResponseResult) -> Unit)
}