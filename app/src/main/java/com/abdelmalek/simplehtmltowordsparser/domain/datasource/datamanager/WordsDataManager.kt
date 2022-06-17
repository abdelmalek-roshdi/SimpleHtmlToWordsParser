package com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager

import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordResult

interface WordsDataManager {
    fun getWords(callback: (WordResult) -> Unit)
    fun saveWords(words: Map<String, Int>?): Boolean
    fun getRemoteData(): Map<String, Int>?
    fun getLocalData(): Map<String, Int>?
}