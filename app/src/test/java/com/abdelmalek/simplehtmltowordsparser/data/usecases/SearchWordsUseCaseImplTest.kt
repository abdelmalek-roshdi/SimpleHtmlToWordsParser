package com.abdelmalek.simplehtmltowordsparser.data.usecases

import com.abdelmalek.simplehtmltowordsparser.domain.entities.Word
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchWordsUseCaseImplTest {

    @Test
    fun searchWords_shouldReturnFilteredList() {
        val list = listOf(
            Word("a", 1),
            Word("b", 2),
            Word("c", 3)
        )
        val expectedValueList = listOf(
            Word("a", 1)
        )
        val useCase = SearchWordsUseCaseImpl()

        assertEquals(expectedValueList,  useCase.searchWords("a", list))
    }
}
