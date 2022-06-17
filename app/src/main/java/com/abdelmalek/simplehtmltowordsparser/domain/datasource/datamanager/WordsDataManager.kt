package com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager

import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult

interface WordsDataManager {
    fun getWords(getWordsCallback: (WordsResponseResult) -> Unit)
    fun saveWords(words: Map<String, Int>): Boolean
}