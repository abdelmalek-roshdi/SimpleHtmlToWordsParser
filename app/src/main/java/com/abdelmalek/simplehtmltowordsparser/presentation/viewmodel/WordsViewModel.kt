package com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.models.UseCasesModel

class WordsViewModel(private val useCases: UseCasesModel) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words

    private val _state = MutableLiveData<WordsResponseResult>(WordsResponseResult.Loading)
    val state: LiveData<WordsResponseResult>
        get() = _state

    init {
        fetchWords()
    }

    private fun fetchWords() {
        useCases.getWordsUseCase.getWordsList { result ->
            setState(result)
            if (result is WordsResponseResult.Success) {
                handleSuccessState(result)
            }
        }
    }

    private fun handleSuccessState(result: WordsResponseResult.Success) {
        if (result.words.isEmpty()) {
            _state.postValue(WordsResponseResult.Empty)
        }
        _words.postValue(MapToListConverter()(result.words))
    }

    private fun setState(result: WordsResponseResult) {
        _state.postValue(result)
    }


}