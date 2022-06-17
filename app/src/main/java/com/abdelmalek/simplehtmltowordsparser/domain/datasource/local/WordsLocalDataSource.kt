package com.abdelmalek.simplehtmltowordsparser.domain.datasource.local

interface WordsLocalDataSource {
    fun saveWords(words: Map<String, Int>): Boolean
    fun getWords(): Map<String, Int>
}