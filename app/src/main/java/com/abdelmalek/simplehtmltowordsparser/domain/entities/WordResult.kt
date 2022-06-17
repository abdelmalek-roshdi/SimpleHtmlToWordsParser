package com.abdelmalek.simplehtmltowordsparser.domain.entities

sealed class WordResult {
    object Loading: WordResult()
    data class Success(val words: Map<String, Int>) : WordResult()
    data class Failure(val exception: Exception) : WordResult()
}
