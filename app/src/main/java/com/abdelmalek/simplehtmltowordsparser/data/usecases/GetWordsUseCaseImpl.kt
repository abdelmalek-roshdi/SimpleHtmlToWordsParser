package com.abdelmalek.simplehtmltowordsparser.data.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.WordsDataManager
import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.GetWordsUseCase

class GetWordsUseCaseImpl(private val dataManager: WordsDataManager) : GetWordsUseCase {
    override fun getWordsList(callback: (WordsResponseResult) -> Unit) {
        return dataManager.getWords(callback)
    }
}