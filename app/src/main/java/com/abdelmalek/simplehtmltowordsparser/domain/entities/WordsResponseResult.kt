package com.abdelmalek.simplehtmltowordsparser.domain.entities

sealed class WordsResponseResult {
    object Loading: WordsResponseResult()
    data class Success(val words: Map<String, Int>) : WordsResponseResult()
    data class Failure(val exception: Exception) : WordsResponseResult()
}
