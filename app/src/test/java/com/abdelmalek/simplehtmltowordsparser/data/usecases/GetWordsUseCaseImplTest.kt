package com.abdelmalek.simplehtmltowordsparser.data.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.WordsDataManager
import com.abdelmalek.simplehtmltowordsparser.domain.entities.WordsResponseResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class GetWordsUseCaseImplTest {

    private lateinit var dataManager: WordsDataManager

    @Before
    fun setup() {
        dataManager = mockk()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getWords_whenSuccess_callbackIsSuccess() {
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { dataManager.getWords(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Success(mapOf()))
        }
        val useCase = GetWordsUseCaseImpl(dataManager)
        useCase.getWordsList {
            assert(it is WordsResponseResult.Success)
        }
    }

    @Test
    fun getWords_whenError_callbackIsFailure() {
        val callbackSlot = slot<(WordsResponseResult) -> Unit>()
        every { dataManager.getWords(capture(callbackSlot)) } answers {
            callbackSlot.captured.invoke(WordsResponseResult.Failure(Exception()))
        }
        val useCase = GetWordsUseCaseImpl(dataManager)
        useCase.getWordsList {
            assert(it is WordsResponseResult.Failure)
        }
    }

}
