package com.abdelmalek.simplehtmltowordsparser.domain.entities

sealed class WordsResponseResult {
    object Loading: WordsResponseResult()
    object Empty: WordsResponseResult()
    data class Success(val words: Map<String, Int>) : WordsResponseResult()
    data class Failure(val exception: Exception) : WordsResponseResult()

    fun isLoading() = this is Loading
    fun isEmpty() = this is Empty
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure
}
