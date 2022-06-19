package com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult
import com.abdelmalek.simplehtmltowordsparser.domain.sorting.WordsSorting
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.models.UseCasesModel

class WordsViewModel(private val useCases: UseCasesModel) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>>
        get() = _words
    private val _allWords = MutableLiveData<List<Word>>()

    private var _sorting = WordsSorting.ASC

    private val _state = MutableLiveData<WordsResponseResult>(WordsResponseResult.Loading)
    val state: LiveData<WordsResponseResult>
        get() = _state

    init {
        fetchWords()
    }

    val searchListener: SearchView.OnQueryTextListener by lazy {
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchWord(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchWord(newText)
                return true
            }
        }
    }

    private fun searchWord(query: String) {
        if (query.isEmpty() || query.isBlank()) {
            _words.postValue(_allWords.value)
        } else {
            _allWords.value?.let {
                _words.postValue(useCases.searchWordsUseCase.searchWords(query, it))
            }
        }
    }

    fun sortWords() {
        _words.value?.let {
            _words.postValue(useCases.sortWordsUseCase.sortWords(it, _sorting))
        }
        _sorting = if (_sorting == WordsSorting.ASC) {
            WordsSorting.DESC
        } else {
            WordsSorting.ASC
        }
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
        val words = MapToListConverter()(result.words)
        _allWords.postValue(words)
        _words.postValue(words)
    }

    private fun setState(result: WordsResponseResult) {
        _state.postValue(result)
    }

}
