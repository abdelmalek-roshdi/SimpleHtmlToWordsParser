package com.abdelmalek.simplehtmltowordsparser.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdelmalek.simplehtmltowordsparser.data.usecases.SearchWordsUseCaseImpl
import com.abdelmalek.simplehtmltowordsparser.data.usecases.SortWordsUseCaseImpl
import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.GetWordsUseCase
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.SearchWordsUseCase
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.SortWordsUseCase
import com.abdelmalek.simplehtmltowordsparser.domain.usecases.models.UseCasesModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WordsViewModelTest {

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WordsViewModel
    private lateinit var useCasesModel: UseCasesModel
    private lateinit var getWordsUseCase: GetWordsUseCase
    private lateinit var searchWordsUseCase: SearchWordsUseCase
    private lateinit var sortWordsUseCase: SortWordsUseCase

    @Before
    fun setup() {
        searchWordsUseCase = SearchWordsUseCaseImpl()
        sortWordsUseCase = SortWordsUseCaseImpl()
        getWordsUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getWords_whenSuccess_shouldReturnList() {
        val wordsMap = mapOf("word" to 1)
        val expectedValue: List<Word> = listOf(Word(wordsMap.keys.first(), wordsMap.values.first()))
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(wordsMap))
        }
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }

        assertEquals(expectedValue, viewModel.words.value)
    }


    @Test
    fun getState_initialValueShouldBeLoading() {
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)

        assertEquals(WordsResponseResult.Loading, viewModel.state.value)
    }

    @Test
    fun getState_whenSuccess_valueShouldBeSuccess() {
        val wordsMap = mapOf("word" to 1)
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(wordsMap))
        }
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }
        viewModel.state.observeForever { }

        assertTrue(viewModel.state.value is WordsResponseResult.Success)
    }

    @Test
    fun getState_whenSuccessButEmpty_valueShouldBeEmpty() {
        val wordsMap = mapOf<String, Int>()
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(wordsMap))
        }
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }
        viewModel.state.observeForever { }

        assertTrue(viewModel.state.value is WordsResponseResult.Empty)
    }

    @Test
    fun getState_whenError_valueShouldBeFailure() {
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Failure(Exception()))
        }
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }
        viewModel.state.observeForever { }

        assertTrue(viewModel.state.value is WordsResponseResult.Failure)
    }

    @Test
    fun search_wordListIsUpdated() {
        val wordsMap = mapOf(
            "word" to 1,
            "alpha" to 2
        )
        val expectedValue: List<Word> = listOf(Word("alpha", 2))
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(wordsMap))
        }
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }

        assertEquals(wordsMap.size, viewModel.words.value?.size)

        viewModel.searchListener.onQueryTextSubmit("a")

        assertEquals(expectedValue, viewModel.words.value)
    }

    @Test
    fun search_whenQueryIsEmpty_wordListIsUpdated() {
        val wordsMap = mapOf(
            "word" to 1,
            "alpha" to 2
        )
        val expectedValue: List<Word> = listOf(Word("alpha", 2))
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(wordsMap))
        }
        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }

        assertEquals(wordsMap.size, viewModel.words.value?.size)

        viewModel.searchListener.onQueryTextSubmit("a")

        assertEquals(expectedValue, viewModel.words.value)

        viewModel.searchListener.onQueryTextSubmit("")

        assertEquals(wordsMap.size, viewModel.words.value?.size)
    }

    @Test
    fun sortWords_shouldUpdatedWordOrder() {
        val wordsMap = mapOf(
            "word" to 1,
            "alpha" to 3,
            "beta" to 2
        )
        val expectedValue: MutableList<Word> = mutableListOf()
        wordsMap.entries.forEach {
            expectedValue.add(Word(it.key, it.value))
        }

        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { getWordsUseCase.getWordsList(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(wordsMap))
        }

        useCasesModel = UseCasesModel(getWordsUseCase, searchWordsUseCase, sortWordsUseCase)
        viewModel = WordsViewModel(useCasesModel)
        viewModel.words.observeForever { }

        viewModel.sortWords()

        expectedValue.sortBy { it.count }

        assertEquals(expectedValue, viewModel.words.value)

        viewModel.sortWords()

        val expectedValueSortedDescending = expectedValue.sortedByDescending { it.count }

        assertEquals(expectedValueSortedDescending, viewModel.words.value)
    }
}
