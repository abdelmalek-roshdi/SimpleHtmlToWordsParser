package com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.models.UseCasesModel

class WordsViewModelFactory(private val useCasesModel: UseCasesModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WordsViewModel(useCasesModel) as T
    }
}