package com.abdelmalek.simplehtmltowordsparser.domain.usecases.models

import com.abdelmalek.simplehtmltowordsparser.domain.usecases.GetWordsUseCase
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.SearchWordsUseCase
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.SortWordsUseCase

data class UseCasesModel(
    val getWordsUseCase: GetWordsUseCase,
    val searchWordsUseCase: SearchWordsUseCase,
    val sortWordsUseCase: SortWordsUseCase
)
